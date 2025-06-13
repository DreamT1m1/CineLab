document.addEventListener("DOMContentLoaded", () => {
    const allVideos = document.getElementById("all-videos");
    const videoHoverWindow = document.getElementById("video-hover-window")

    const loadIframesOnce = () => {
        const iframes = videoHoverWindow.getElementsByClassName("video");
        Array.from(iframes).forEach(iframe => {
            if (!iframe.src && iframe.dataset.src) {
                iframe.src = iframe.dataset.src;
            }
        });
    };

    allVideos.addEventListener("click", (event) => {
        event.stopPropagation();
        videoHoverWindow.classList.add("media-window-shown");
        loadIframesOnce();
    });

    document.addEventListener("click", (event) => {
        if (
            videoHoverWindow.classList.contains("media-window-shown")
            && !videoHoverWindow.contains(event.target)
            && event.target !== allVideos
        ) {
            videoHoverWindow.classList.remove("media-window-shown");
        }
    });

    videoHoverWindow.addEventListener("click", (event) => {
        event.stopPropagation();
    });
});
