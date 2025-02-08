let localVideo = document.getElementById("local-video");
let remoteVideo = document.getElementById("remote-video");

// Set initial opacity
localVideo.style.opacity = 0;
remoteVideo.style.opacity = 0;

localVideo.onplaying = () => { localVideo.style.opacity = 1; };
remoteVideo.onplaying = () => { remoteVideo.style.opacity = 1; };

let peer;
let localStream;

// Peer connection
function init(userId) {
    peer = new Peer(userId, {
        port: 443,
        path: '/'
    });

    peer.on('open', () => {
        Android.onPeerConnected();
    });

    listenForCalls();
}


function listenForCalls() {
    peer.on('call', (call) => {
        navigator.mediaDevices.getUserMedia({ audio: true, video: true })
            .then(stream => {
                localVideo.srcObject = stream;
                localStream = stream;

                call.answer(stream);
                call.on('stream', remoteStream => {
                    remoteVideo.srcObject = remoteStream;

                    remoteVideo.className = "primary-video";
                    localVideo.className = "secondary-video";
                });
            })
            .catch(error => {
                console.error('Error accessing media devices.', error);
            });
    });
}

// Start call
function startCall(otherUserId) {
    navigator.mediaDevices.getUserMedia({ audio: true, video: true })
        .then(stream => {
            localVideo.srcObject = stream;
            localStream = stream;

            const call = peer.call(otherUserId, stream);
            call.on('stream', remoteStream => {
                remoteVideo.srcObject = remoteStream;

                remoteVideo.className = "primary-video";
                localVideo.className = "secondary-video";
            });
        }).catch(error => {
            console.error('Error accessing media devices.', error);
        });
}
// Toggle video on or off
function toggleVideo(enabled) {
    if (localStream) {
        const videoTrack = localStream.getVideoTracks()[0];
        if (videoTrack) {
            videoTrack.enabled = enabled === "true";
        }
    }
}
// Toggle audio on or off
function toggleAudio(enabled) {
    if (localStream) {
        const audioTrack = localStream.getAudioTracks()[0];
        if (audioTrack) {
            audioTrack.enabled = enabled === "true";
        }
    }
}
