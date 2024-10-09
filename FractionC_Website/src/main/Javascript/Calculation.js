document.addEventListener("DOMContentLoaded", function() {
    function showInfoBox(title, message, isError = false) {
        const infoBox = document.getElementById("infoBox");
        const infoBoxTitle = document.getElementById("infoBoxTitle");
        const infoBoxMessage = document.getElementById("infoBoxMessage");

        infoBoxTitle.textContent = title;
        infoBoxMessage.textContent = message;

        if (isError) {
            infoBox.classList.add("error");
        } else {
            infoBox.classList.remove("error");
        }

        infoBox.style.display = "block";
        setTimeout(() => {
            infoBox.style.display = "none";
        }, 3000); // Hide after 3 seconds
    }

    function submitCalculation(event) {
        event.preventDefault();
        const L1 = document.getElementById("L_First").value;
        const L2 = document.getElementById("L_Second").value;
        const R1 = document.getElementById("R_First").value;
        const R2 = document.getElementById("R_Second").value;
        const operation = document.getElementById("operation").value;

        fetch('/calculate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ L1, L2, R1, R2, operation })
        })
        .then(response => response.json())
        .then(data => {
            document.getElementById("Result_First").value = data.result1;
            document.getElementById("Result_Second").value = data.result2;
            showInfoBox(data.title, data.message, data.title === "Error");
        })
        .catch(error => {
            console.error('Error:', error);
            showInfoBox("Error", "An error occurred during calculation.", true);
        });
    }

    function resetFields(event) {
        event.preventDefault();
        document.getElementById("L_First").value = '';
        document.getElementById("L_Second").value = '';
        document.getElementById("R_First").value = '';
        document.getElementById("R_Second").value = '';
        document.getElementById("Result_First").value = '';
        document.getElementById("Result_Second").value = '';
        showInfoBox("Reset", "Fields have been reset.");
    }

    document.getElementById("calculateForm").addEventListener("submit", submitCalculation);
    document.getElementById("B_Reset").addEventListener("reset", resetFields);
});