# AndroidRTC

## WebRTC Live Streaming

An Android client for [ProjectRTC](https://github.com/pchab/ProjectRTC).

It is designed to demonstrate WebRTC video calls between androids and/or desktop browsers, but WebRtcClient could be used in other scenarios.

## How To

You need [ProjectRTC](https://github.com/pchab/ProjectRTC) up and running, and it must be somewhere that your android can access. (You can quickly test this with your android browser). Modify the host string (in res/values/strings.xml) to the server IP.

When you launch the app, you will be given several options to send a message : "Call someone"
Use this menu to send a link of your stream. This link can be opened with a WebRTC-capable browser or by another AndroidRTC.
The video call should then start.

Your stream should appear as "android_test" in ProjectRTC, so you can also use the call feature there.

## Libraries

### [libjingle peerconnection](https://code.google.com/p/webrtc/)
### [socket.io-client](https://github.com/nkzawa/socket.io-client.java)

If you want to use them in your project, I recommend working with IntelliJ IDEA :

- Start a new android application project
- Add the .jar and .so in the libs folder
- Right-click "add as library" 

## Author

- Pierre Chabardes
