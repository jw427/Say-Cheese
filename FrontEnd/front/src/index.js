import React from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

import "./index.css";
import App from "./App";

import Main from "./Main";
import Frame from "./frame/Frame";
import ErrorPage from "./error-page";

import CustomerCenter from "./customercenter/CustomerCenter";
import NoticeList from "./customercenter/NoticeList";
import Notice from "./customercenter/Notice";
import Faq from "./customercenter/Faq";

import User from "./user/User";
import Login from "./user/Login";
import InfoAgreement from "./user/InfoAgreement";
import SignUp from "./user/SignUp";

import Photo from "./photo/Photo";

const router = createBrowserRouter([
  {
    path: "main",
    element: <Main />,
  },
  {
    path: "/",
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: "frame/",
        element: <Frame />,
      },
      // 이제 이미지 게시판, 공지 게시판, 로그인 페이지, 마이페이지를 연결하면 됩니다.
      {
        path: "customercenter/",
        element: <CustomerCenter />,
        children: [
          {
            path: "notice/",
            element: <NoticeList />,
          },
          {
            path: "notice/:id",
            element: <Notice />,
          },
          {
            path: "faq/",
            element: <Faq />,
          },
        ],
      },
      {
        path: "user/",
        element: <User />,
        children: [
          {
            path: "login/",
            element: <Login />,
          },
          {
            path: "infoagree/",
            element: <InfoAgreement />,
          },
          {
            path: "signup/",
            element: <SignUp />,
          },
        ],
      },
      {
        path: "photo/",
        element: <Photo />,
      },
    ],
  },
  // ERROR 페이지 라우터
  {
    path: "/*",
    element: <ErrorPage />,
    errorElement: <ErrorPage />,
  },
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
