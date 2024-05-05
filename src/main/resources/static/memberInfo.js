/*
document.addEventListener("DOMContentLoaded", function() {
    const deleteMemberButton = document.querySelectorAll(".delete-member");
    console.log(deleteMemberButton.length); // 버튼의 수를 로그로 출력


    deleteMemberButton.forEach(button => {
        button.addEventListener("click", function () {
            console.log("Button clicked!"); // 클릭 이벤트가 발생했는지 확인
            const memberId = this.getAttribute("data-id");

            if (confirm("탈퇴하시겠습니까?")){
                fetch(`/api/members/${memberId}`, {
                    method: 'DELETE',
                })
                    .then(response => {
                        if (response.ok) {
                            alert("회원이 탈퇴되었습니다.");
                            window.location.href = "/"; // 리다이렉트
                        } else {
                            response.text().then(text => alert("회원 탈퇴에 실패했습니다. 오류: " + text));
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert("회원 탈퇴 중 문제가 발생");
                    });
            }
        })
    })
}*/


document.body.addEventListener('click', function(event) {
    if (event.target.classList.contains('delete-member')) {
        const memberId = event.target.getAttribute('data-id');
        console.log("Button clicked! Member ID: " + memberId); // 클릭 이벤트와 멤버 ID 확인

        if (confirm("탈퇴하시겠습니까?")) {
            fetch(`/api/members/${memberId}`, {
                method: 'DELETE',
            })
                .then(response => {
                    if (response.ok) {
                        alert("회원이 탈퇴되었습니다.");
                        window.location.href = "/";
                    } else {
                        response.text().then(text => alert("회원 탈퇴에 실패했습니다. 오류: " + text));
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert("회원 탈퇴 중 문제가 발생");
                });
        }
    }
});

