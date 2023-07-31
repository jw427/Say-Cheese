// modal 관련  redux tookit 모듈 파일
import { createSlice } from "@reduxjs/toolkit";

// 초기 상태 정의
const initialState = {
  height: 589.6,
  width: 207.8,
};

// 액션 생성 함수
const frameSlice = createSlice({
  // 액션 타입 정의
  name: "frame",
  // 초기 상태
  initialState,
  // 리듀서 맵
  reducers: {
    // 리듀서 함수
    // 아래 코드는 리듀서와 액션 생성자 함수가 분리되지 않은 형태로 작성함
    Resize: (state, action) => {
      state.width = action.payload.width;
      state.height = action.payload.height;
      console.log(state.width);
    },
    // HorizontalResize: (state, action) => {
    //   state.width = 589.6;
    //   state.height = 207.8;
    //   console.log(state.width, state.height);
    // },
  },
});

export const { Resize } = frameSlice.actions;

export default frameSlice.reducer;
