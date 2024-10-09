document.addEventListener("DOMContentLoaded", function() {
    function submitCalculation(event) {
        // Prevent the form from submitting and reloading the page
        event.preventDefault();

        // Get values from input fields
        const L1 = document.getElementById("L_First").value;
        const L2 = document.getElementById("L_Second").value;
        const R1 = document.getElementById("R_First").value;
        const R2 = document.getElementById("R_Second").value;
        const operation = document.getElementById("operation").value;

        // Send the data to the server
        fetch('/calculate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ L1, L2, R1, R2, operation })
        })
        .then(response => response.json())
        .then(data => {
            // Display the result in the corresponding fields
            document.getElementById("Result_First").value = data.result1;
            document.getElementById("Result_Second").value = data.result2;
        })
        .catch(error => console.error('Error:', error));
    }

    function resetFields(event) {
        // Prevent the form from submitting and reloading the page
        event.preventDefault();

        // Clear all input fields
        document.getElementById("L_First").value = '';
        document.getElementById("L_Second").value = '';
        document.getElementById("R_First").value = '';
        document.getElementById("R_Second").value = '';
        document.getElementById("Result_First").value = '';
        document.getElementById("Result_Second").value = '';
        // Print out something
        console.log("Fields reset");
    }

    // Attach the submitCalculation function to the form's submit event
    document.getElementById("calculateForm").addEventListener("submit", submitCalculation);

    // Attach the resetFields function to the Reset button's click event
    document.getElementById("B_Reset").addEventListener("reset", resetFields);
});