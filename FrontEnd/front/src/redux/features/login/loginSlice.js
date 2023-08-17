// login 상태 관련  redux tookit 모듈 파일
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";

// 초기 상태 정의
const initialState = {
  isLogin: false, // 초기 로그인 상태 (로그인 되어있지 않음 = false)
  userInfo: {},
};

// 비회원 방 입장 시 닉네임 받기
export const notUserNickname = createAsyncThunk(
  "login/notUserNickname",
  async (nickname) => {
    const userInfo = {
      nickname: nickname,
    };
    return userInfo;
  }
);

// 비동기 액션 생성자
export const getUserInfo = createAsyncThunk("login/getUserInfo", async () => {
  try {
    const accessToken = localStorage.getItem("accessToken");
    if (accessToken) {
      const response = await axios.get("/api/member/info", {
        headers: {
          "Content-Type": "application/json",
          "ngrok-skip-browser-warning": "69420",
          Authorization: `${accessToken}`,
        },
      });
      return response.data;
    }
  } catch (error) {
    // status가 401인 경우 액세스 토큰 재발급
    if (error.response && error.response.status === 401) {
      try {
        const newAccessToken = refreshToken();

        const response = await axios.get("/api/member/info", {
          headers: {
            "Content-Type": "application/json",
            "ngrok-skip-browser-warning": "69420",
            Authorization: `${newAccessToken}`,
          },
        });
        return response.data;
      } catch (refreshError) {
        throw refreshError;
      }
    }
    console.log(error);
    throw error;
  }
});

// 리프레시토큰으로 액세스 토큰 재발급
export const refreshToken = createAsyncThunk(
  "login/refreshToken",
  async (_, thunkAPI) => {
    try {
      const localRefreshToken = localStorage.getItem("refreshToken");
      if (refreshToken) {
        const response = await axios.post("/api/auth/refresh", {
          headers: {
            "Content-Type": "application/json",
            Authorization: `${localRefreshToken}`,
          },
        });
        const accessToken = response.headers["Authorization"];

        axios.defaults.headers.common["Authorization"] = `${accessToken}`;

        localStorage.setItem("accessToken", accessToken);

        thunkAPI.dispatch(getUserInfo());
      }
    } catch (error) {
      throw error;
    }
  }
);

// 액션 생성 함수
const loginSlice = createSlice({
  name: "login",

  initialState,

  reducers: {
    loginSuccess: (state, action) => {
      state.isLogin = true;
    },
    logoutSuccess: (state, action) => {
      state.isLogin = false;
      state.userInfo = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(getUserInfo.fulfilled, (state, action) => {
        state.userInfo = action.payload;
      })
      .addCase(refreshToken.fulfilled, (state, action) => {
        state.isLogin = true;
      })
      .addCase(notUserNickname.fulfilled, (state, action) => {
        state.userInfo = action.payload;
      });
  },
});

export const { loginSuccess, logoutSuccess } = loginSlice.actions;

export default loginSlice.reducer;
