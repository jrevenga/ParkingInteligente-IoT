$(document).ready(function () {
    console.log("Document ready event has occurred.");
    var parkingId = 1;
    var parkingCiudad = 1;
    var idSensor= 11; //temperatura
    $.ajax({
        data: {parkingId : parkingId, parkingCiudad : parkingCiudad, idSensor : idSensor}, 
        url: './GetMonthTemp',  
        dataType: 'json',
        type: 'post',
        
        success: function(data) {
            // Prepara los datos para la gráfica
            var labels = data.data.map(item => item.label);
            var values = data.data.map(item => item.value);
            console.log("Datos cargados correctamente:", labels, values);

            // Configura la gráfica
            var ctx = document.getElementById('myChart').getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Datos de la Temperatura',
                        data: values,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)', // Color de fondo
                        borderColor: 'rgba(255, 99, 132, 1)',      // Color del borde
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        },
        error: function(xhr, status, error) {
            console.error("Error al cargar el archivo JSON:", error);
        }
    });


});



