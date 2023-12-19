$(document).ready(function () {
    console.log("Document ready event has occurred. 3");
    var idParking= 1;
    var idCiudad = 1;
    var idTipo= 1; //temperatura
    $.ajax({
        
        data: { idCiudad : idCiudad,idParking : idParking, idTipo : idTipo}, 
        url: './GetMonthTemp',  
        dataType: 'json',
        type: 'post',
        
        success: function(data) {
            // Prepara los datos para la gráfica
            console.log("Respuesta del servidor:", data);
            
            
            var labels = data.map(item => item.timestamp);
            var values = data.map(item => item.value);
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



