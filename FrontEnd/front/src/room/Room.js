import { useState, useEffect, useRef } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { OpenVidu } from "openvidu-browser";
import html2canvas from "html2canvas";
import axios from "axios";
import "./Room.css";
import UserVideoComponent from "./UserVideoComponent";
import RoomButtons from "./RoomButtons";
import RoomFooter from "./RoomFooter";
import RoomHeader from "./RoomHeader";
import RoomPhoto from "./RoomPhoto";
import Timer from "./Timer";
import ChatComponent from "./ChatComponent";

const APPLICATION_SERVER_URL = "http://localhost:5000/";
const APPLICATION_SERVER_SECRET = "MY_SECRET";
var chatData;
const Room = () => {
  const params = useParams();
  const navigate = useNavigate();
  const [mySessionId, setMySessionId] = useState(params.id);
  const [myUserName, setMyUserName] = useState(
    "test" + Math.floor(Math.random() * 100)
  );
  const [session, setSession] = useState(undefined);
  const [mainStreamManager, setMainStreamManager] = useState(undefined);
  const [publisher, setPublisher] = useState(undefined);
  const [subscribers, setSubscribers] = useState([]);
  const [roomStatus, setRoomStatus] = useState(0);

  useEffect(() => {
    window.addEventListener("beforeunload", onbeforeunload);
    console.log("USEEFFECT", params);
    joinSession();
    return () => {
      window.removeEventListener("beforeunload", onbeforeunload);
    };
  }, [params.id]);

  const onbeforeunload = (event) => {
    leaveSession();
  };

  const deleteSubscriber = (streamManager) => {
    setSubscribers((prevSubscribers) =>
      prevSubscribers.filter((sub) => sub !== streamManager)
    );
  };

  const joinSession = () => {
    const OV = new OpenVidu();
    setSession(OV.initSession());
    const mySession = OV.initSession();

    console.log(mySession);

    mySession.on("streamCreated", (event) => {
      const subscriber = mySession.subscribe(event.stream, undefined);
      setSubscribers((prevSubscribers) => [...prevSubscribers, subscriber]);
    });

    mySession.on("streamDestroyed", (event) => {
      deleteSubscriber(event.stream.streamManager);
    });

    mySession.on("exception", (exception) => {
      console.warn(exception);
    });

    getToken().then((token) => {
      mySession
        .connect(token)
        .then(async () => {
          chatData = mySession;
          console.log(mySession, mySessionId);
          const publisher = await OV.initPublisherAsync(undefined, {
            audioSource: undefined,
            videoSource: undefined,
            publishAudio: true,
            publishVideo: true,
            resolution: "320x240",
            frameRate: 30,
            insertMode: "AFTER",
            mirror: false,
          });

          mySession.publish(publisher);

          // 카메라 선택 옵션 넣을 때 사용됨
          const devices = await OV.getDevices();
          // const videoDevices = devices.filter((device) => device.kind === "videoinput");
          // const currentVideoDeviceId = publisher.stream.getMediaStream().getVideoTracks()[0].getSettings().deviceId;
          // const currentVideoDevice = videoDevices.find((device) => device.deviceId === currentVideoDeviceId);

          setMainStreamManager(publisher);
          setPublisher(publisher);
        })
        .catch((error) => {
          console.log(
            "There was an error connecting to the session:",
            error.code,
            error.message
          );
        });
    });
  };

  const leaveSession = () => {
    if (session) {
      session.disconnect();
    }

    setSession(undefined);
    setSubscribers([]);
    setMainStreamManager(undefined);
    setPublisher(undefined);
  };

  const getToken = async () => {
    const sessionId = await createSession(mySessionId);
    return createToken(sessionId);
  };

  const createSession = async (sessionId) => {
    console.log("세션 아이디", sessionId);
    const response = await axios.post(
      APPLICATION_SERVER_URL + "api/sessions",
      { customSessionId: sessionId },
      {
        headers: {
          Authorization: `Basic ${btoa(
            `OPENVIDUAPP:${APPLICATION_SERVER_SECRET}`
          )}`,
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Methods": "GET,POST",
        },
      }
    );
    return response.data;
  };

  const createToken = async (session) => {
    const response = await axios.post(
      APPLICATION_SERVER_URL + "api/sessions/" + session + "/connections",
      {},
      {
        headers: {
          Authorization: `Basic ${btoa(
            `OPENVIDUAPP:${APPLICATION_SERVER_SECRET}`
          )}`,
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Methods": "GET,POST",
        },
      }
    );
    return response.data;
  };

  const roomMainRef = useRef();

  useEffect(() => {
    if (roomStatus === 1) {
      handleCapture();
    }
  }, [roomStatus]);

  const handleCapture = () => {
    if (roomStatus !== 1 || !roomMainRef.current) {
      return;
    }
    html2canvas(roomMainRef.current).then((canvas) => {
      console.log("CANVAS", canvas);
      const image = new Image();
      image.src = canvas.toDataURL("image/png");
      document.body.appendChild(image);
    });
  };

  return (
    <div className="room">
      {roomStatus === 2 ? (
        <div className="room-active">
          <div className="room-top">
            <RoomHeader status={roomStatus} />
            <RoomButtons
              onConfirm={() => {
                navigate("/");
              }}
              onClose={() => {
                setRoomStatus(1);
              }}
              buttonName1="나가기"
              buttonName2="사진 공유"
            />
          </div>
          <div className="room-mid">
            <RoomPhoto />
            {chatData && <ChatComponent user={chatData} />}
          </div>
          <div className="room-bot">
            <RoomFooter status={roomStatus} />
            <Timer minutes="5" seconds="0" />
          </div>
        </div>
      ) : roomStatus === 1 ? (
        <div className="room-active">
          <div className="room-top">
            <RoomHeader status={roomStatus} />
            <RoomButtons
              onConfirm={() => {
                setRoomStatus(2);
              }}
              onClose={() => {
                setRoomStatus(0);
              }}
              buttonName1="촬영하기"
              buttonName2="다시 찍기"
            />
          </div>
          <div className="room-mid">
            {/* <RoomPhoto /> */}
            <div className="room-main">
              <UserVideoComponent
                streamManager={publisher}
                myName={myUserName}
              />
              {subscribers.map((sub, i) => (
                <div
                  key={sub.id}
                  className="stream-container col-md-6 col-xs-6"
                >
                  <span>{sub.id}</span>
                  <UserVideoComponent streamManager={sub} />
                </div>
              ))}
            </div>
            {chatData && <ChatComponent user={chatData} myName={myUserName} />}
          </div>
          <div className="room-bot">
            <RoomFooter status={roomStatus} />
            <Timer minutes="0" seconds="30" />
            <button onClick={handleCapture()}>캡처</button>
          </div>
        </div>
      ) : (
        <div className="room-active">
          <div className="room-top">
            <RoomHeader status={roomStatus} />
            <RoomButtons
              onConfirm={() => {
                setRoomStatus(1);
              }}
              onClose={() => {}}
              buttonName1="시작하기"
              buttonName2="초대 링크"
            />
          </div>
          <div className="room-mid">
            <div className="room-main">
              <UserVideoComponent
                streamManager={publisher}
                myName={myUserName}
              />
              {subscribers.map((sub, i) => (
                <div
                  key={sub.id}
                  className="stream-container col-md-6 col-xs-6"
                >
                  <span>{sub.id}</span>
                  <UserVideoComponent streamManager={sub} />
                </div>
              ))}
            </div>
            {chatData && <ChatComponent user={chatData} myName={myUserName} />}
          </div>
          <div className="room-bot">
            <RoomFooter status={roomStatus} />
          </div>
        </div>
      )}
    </div>
  );
};

export default Room;
