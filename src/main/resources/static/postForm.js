document.addEventListener("DOMContentLoaded", function() {
    const updateButtons = document.querySelectorAll(".update-comment");

    //수정 버튼
    updateButtons.forEach(button => {
        button.addEventListener("click", function() {
            const commentId = this.getAttribute("data-id"); // 수정 버튼을 누른 Comment 의 ID
            const contentSpan = document.getElementById(`content-${commentId}`); //댓글내용
            const textarea = document.getElementById(`textarea-${commentId}`);
            const submitButton = document.getElementById(`submit-${commentId}`);

            //스타일 속성을 변경하여 사용자에게 textarea / submitButton 이 보이게 됨.
            textarea.style.display = '';
            submitButton.style.display = '';
            //원래 댓글 내용은 안보이게 됨.
            contentSpan.style.display = 'none';

            textarea.value = contentSpan.textContent; // 기존 댓글 내용을 텍스트 영역에 복사


            //제출 버튼을 누름.
            submitButton.onclick = function() {
                //현재 textarea 에 있는 내용 불러오기
                const updatedContent = textarea.value;

                //API 요청
                fetch(`/api/comments/${commentId}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ content: updatedContent })
                })
                    //응답 처리
                    .then(response => {
                        if (response.ok) {
                            contentSpan.textContent = updatedContent; // 화면에 수정된 내용 표시
                            textarea.style.display = 'none';
                            submitButton.style.display = 'none';
                            contentSpan.style.display = '';
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error)
                    });
            };
        });
    });

    //HTML 요소 특정
    const deleteButtons = document.querySelectorAll(".delete-comment");

    //삭제 버튼
    deleteButtons.forEach(button => {
        button.addEventListener("click", function () {
            const commentId = this.getAttribute("data-id"); // 삭제 버튼을 누른 Comment 의 ID

            if (confirm("삭제하시겠습니까?")){
                fetch(`/api/comments/${commentId}`, {
                    method: 'DELETE',
                })
                    .then(response => {
                        if (response.ok) {
                            alert("댓글이 삭제되었습니다.");
                            this.closest('tr').remove(); //HTML 에서 댓글 행을 제거
                        } else {
                            alert("댓글 삭제에 실패했습니다.");
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert("댓글 삭제 중 문제가 발생");
                    });
            }

        })
    });

    const deletePostButton = document.querySelectorAll(".delete-post");

    deletePostButton.forEach(button => {
        button.addEventListener("click", function () {
            const postId = this.getAttribute("data-id");

            if (confirm("삭제하시겠습니까?")){
                fetch(`/api/posts/${postId}`, {
                    method: 'DELETE',
                })
                    .then(response => {
                        if (response.ok) {
                            alert("게시글이 삭제되었습니다.");
                            window.location.href = "/"; // 리다이렉트
                        } else {
                            alert("게시글 삭제에 실패했습니다.");
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert("게시글 삭제 중 문제가 발생");
                    });
            }
        })
    })
});
