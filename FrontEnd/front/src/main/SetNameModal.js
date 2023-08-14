import { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { notUserNickname } from "../redux/features/login/loginSlice";

import "./SetNameModal.css";
import ModalButtons from "./ModalButtons";

function SetNameModal({ open, close }) {
  const { userInfo } = useSelector((store) => store.login);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [inputNickname, setInputNickname] = useState("");

  if (!open) {
    return null;
  }
  const handleConfirm = () => {
    console.log(inputNickname);
    dispatch(notUserNickname(inputNickname));
    close();
    console.log(userInfo);
  };
  return (
    <div className="set-name-modal">
      <div className="set-name-modal-content">
        <h2>현재 비로그인 상태입니다</h2>
        <p>
          <button
            onClick={() => {
              navigate(`/user/login`);
              close();
            }}
          >
            로그인 하러가기
          </button>
        </p>
        <p>닉네임 설정하고 계속하기</p>
        <input
          type="text"
          placeholder="닉네임을 입력해주세요"
          value={inputNickname}
          onChange={(event) => {
            setInputNickname(event.target.value);
          }}
          maxLength={10}
        />
        <br />
        <ModalButtons onConfirm={handleConfirm} onClose={close} />
      </div>
    </div>
  );
}

export default SetNameModal;
