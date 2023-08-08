const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = function (app) {
  app.use(
    createProxyMiddleware("/openvidu", {
      target: "https://i9a401.p.ssafy.io",
      changeOrigin: true,
    })
  );
  app.use(
    createProxyMiddleware("/api/room", {
      target: "https://d107-211-192-210-169.ngrok-free.app",
      changeOrigin: true,
    })
  );
  app.use(
    createProxyMiddleware("/api", {
      target: "https://9177-211-192-210-179.ngrok-free.app",
      changeOrigin: true,
    })
  );
};
