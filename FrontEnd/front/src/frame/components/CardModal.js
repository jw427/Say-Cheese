import React, { useState, useEffect } from "react";

import "../css/CardModal.css";
// react-redux
import { useDispatch, useSelector } from "react-redux";
import { closeModal } from "../../redux/features/modal/modalSlice";

export default function CardModal() {
  // 모달에 표시할 내용이 없으면 에러가 나지않게 로딩 상태를 표시
  const [loading] = useState(false);

  // 모달에 띄울 컨텐츠를 가져옵니다
  // state는 젠처 리덕스 스토어를 의미하며, modal 리듀서에서 관리되는 상태 객체 modalContent를 추출합니다.
  const { isOpen } = useSelector((store) => store.modal);
  const { modalContent } = useSelector((state) => state.modal);

  const dispatch = useDispatch();
  // 좋아요 체크 되어있으면 like:1 안 했으면 :0
  const [like, setLike] = useState(modalContent.loverYn);

  function clickLike(event) {
    event.stopPropagation();
    setLike(!like);
    // api 추가해야함
  }

  useEffect(() => {
    // 모달 열리면 본문 스크롤 방지
    document.body.style.overflow = "hidden";
    // 현재 보고 있는 스크롤의 왼쪽 top을 가운데 정렬할 기준 top으로 해줌
    const modalbg = document.getElementsByClassName("modalBackdrop")[0]; // Get the first element with the class name
    const currentTop = window.scrollY + "px";
    modalbg.style.top = currentTop; // Set the top CSS property of the element
  }, []); // Empty dependency array to run the effect only once when the component mounts

  return (
    <>
      <div className="modalBackdrop">
        <div className="modal">
          {loading ? (
            <div>loading..</div>
          ) : (
            <>
              <div className="modal-name">{modalContent.name}</div>
              <div className="modal-author">
                {" "}
                작성자 : {modalContent.author}
              </div>
              {/* .imgSrc에 추후 주의가 필요합니다. 이후 재설정이 필요합니다 */}
              <img src={modalContent.imageLink.sampleImg} alt="프레임 이미지" />

              <div className="heart-btn" onClick={clickLike}>
                <div className="heart-content">
                  <span
                    className={
                      like === 1
                        ? "heart full"
                        : like === true
                        ? "heart-active heart"
                        : "heart"
                    }
                  ></span>
                  <span className="numb">{modalContent.loverCnt}</span>
                </div>
              </div>

              <button
                className="modalClose"
                onClick={() => {
                  // 모달 닫으면 본문 스크롤 허용
                  document.body.style.overflow = "auto";
                  dispatch(closeModal());
                }}
              >
                X
              </button>
            </>
          )}
        </div>
      </div>
    </>
  );
}
