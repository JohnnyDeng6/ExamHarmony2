function changePassword() {
    var currentPath = window.location.pathname;
    var basePath = currentPath.substring(0, currentPath.lastIndexOf('/'));
    var newUrl = basePath + "/sendPrt";

    fetch(newUrl, {
        method: 'POST'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(result => {
            if (result.trim() === 'success') {
                alert("A link has been sent to your email");
                location.replace(basePath + "/home");
            } else {
                alert("An error occurred: " + result);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            // Handle network or other errors
            alert("An error occurred: " + error.message);
        });
}
