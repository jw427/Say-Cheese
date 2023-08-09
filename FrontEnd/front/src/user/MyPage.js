import { useEffect } from "react";
import "./MyPage.css";
import MyProfile from "./MyProfile";
import MyPhotoCard from "./MyPhotoCard";
import MyPhotoNull from "./MyPhotoNull";
import MyPageModal from "./MyPageModal";
import sampleImg from "./assets/snoopy.png";
import { useDispatch, useSelector } from "react-redux";
import { useParams, useNavigate } from "react-router-dom";
import { getUserInfo } from "../redux/features/login/loginSlice";

function MyPage() {
  const { isLogin, userInfo } = useSelector((store) => store.login);
  const { email } = useParams(); // 경로의 email
  const movePage = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    if (!isLogin || !userInfo || userInfo.email !== email) {
      movePage("/"); // "/" 경로로 리다이렉션
    }
  }, [isLogin, userInfo, email, movePage]);

  const myPhotoList = [
    {
      id: 0,
      name: "snoopy",
      imgSrc: { sampleImg },
      likes: 10,
    },
    {
      id: 1,
      name: "snoopy2",
      imgSrc: { sampleImg },
      likes: 12,
    },
    {
      id: 2,
      name: "snoopy3",
      imgSrc: { sampleImg },
      likes: 20,
    },
    {
      id: 3,
      name: "snoopy4",
      imgSrc: { sampleImg },
      likes: 50,
    },
  ];

  const MyFrameList = [
    {
      id: 0,
      name: "snoopy",
      imgSrc: { sampleImg },
      likes: 10,
    },
    {
      id: 1,
      name: "snoopy2",
      imgSrc: { sampleImg },
      likes: 12,
    },
  ];

  const { isOpen } = useSelector((store) => store.modal);

  return (
    <div>
      {!userInfo ? (
        movePage("/")
      ) : (
        <div className="MyPageBox">
          <div className="MyProfileSort">
            <MyProfile
              email={userInfo.email}
              nickname={userInfo.nickname}
              genderFm={userInfo.genderFm}
              age={userInfo.age}
              name={userInfo.name}
              profile={userInfo.profile}
            />
            {isOpen && <MyPageModal />}
          </div>

          <div className="MyPageTitle">
            <h2>내가 찍은 사진</h2>
            <h3>더보기</h3>
          </div>
          <div className="MyPagePhoto">
            {myPhotoList.slice(0, 3).map((item) => (
              <MyPhotoCard
                key={item.id}
                name={item.name}
                imgSrc={item.imgSrc.sampleImg}
                likes={item.likes}
              />
            ))}
            {/* 마이페이지에서 내가 찍은 사진 3개 넘을 경우 3개만 보여주기 위함 */}
            {myPhotoList.length > 0 &&
              myPhotoList.length < 3 &&
              Array(3 - myPhotoList.length)
                .fill(null)
                .map((num, index) => <MyPhotoNull key={index} />)}
            {/* 내가 찍은 사진이 1개일 경우 <MyPhotoNull /> 2개 출력하고, 2개일 경우 <MyPhotoNull /> 1개 출력
            마이페이지에서 내가 찍은 사진 왼쪽 정렬 깔끔히 하기 위해 내용 없는 <MyPhotoNull /> 임의로 생성 */}
          </div>
          <div className="MyPageTitle">
            <h2>내가 만든 프레임</h2>
            <h3>더보기</h3>
          </div>
          <div className="MyPagePhoto">
            {MyFrameList.slice(0, 3).map((item) => (
              <MyPhotoCard
                key={item.id}
                name={item.name}
                imgSrc={item.imgSrc.sampleImg}
                likes={item.likes}
              />
            ))}
            {MyFrameList.length > 0 &&
              MyFrameList.length < 3 &&
              Array(3 - MyFrameList.length)
                .fill(null)
                .map((num, index) => <MyPhotoNull key={index} />)}
          </div>
        </div>
      )}
    </div>
  );
}

export default MyPage;
