<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="accountLockedModal" style="display:none; position:fixed; top:0; left:0; width:100vw; height:100vh; background:rgba(0,0,0,0.95); z-index:10000; flex-direction:column; align-items:center; justify-content:center; color:white; text-align:center;">
    <div style="background:#fff; color:#333; padding:40px; border-radius:12px; max-width:500px; box-shadow: 0 10px 30px rgba(0,0,0,0.5);">
        <h2 class="text-danger mb-3">Session Terminated</h2>
        <p class="mb-4">Your account has been deactivated or deleted by the administrator. For security reasons, you cannot proceed.</p>
        <button onclick="window.location.href='/login'" class="btn btn-primary btn-lg">Back to Login</button>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.1/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

<script>
    $(document).ready(function() {
        const socket = new SockJS('/ws-security');
        const stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            // Subscribe to the private user queue
            stompClient.subscribe('/user/queue/deactivation', function (message) {
                if (message.body === "LOCKED") {
                    lockScreen();
                }
            });
        });
    });

    function lockScreen() {
        $("#accountLockedModal").css("display", "flex");
        // Disable interactions
        $('body').css('overflow', 'hidden');
        // Final blow: invalidate session via background post
        $.post("/perform_logout");
    }
</script>