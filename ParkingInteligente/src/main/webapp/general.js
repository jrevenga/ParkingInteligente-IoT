
document.addEventListener('DOMContentLoaded', function() {
    //cabecera
    actualizarFechaHora();
    setInterval(actualizarFechaHora, 1000); // Actualizar cada segundo

    //boton soporte tecnico y notas de parche
    var btnSoporte = document.getElementById("btnSoporte");
    var btnNotas = document.getElementById("btnNotas");

    btnSoporte.addEventListener("click", function(){
        window.location.href = "soporteTecnico.html";
    });



    //boton sede
    var btnSede = document.getElementById("btnSede");
    var desplegable = document.querySelector(".desplegable");

    // Mostrar la caja al pasar por encima del botón
    btnSede.addEventListener("mouseenter", function() {
        desplegable.style.display = "block";
    });



    // Ocultar la caja al salir del botón o la caja misma
    btnSede.addEventListener("mouseleave", function(e) {
        if (!e.relatedTarget || !desplegable.contains(e.relatedTarget)) {
            desplegable.style.display = "none";
        }
    });

    // Opcional: Ocultar la caja al hacer clic fuera de ella
    document.addEventListener("click", function(e) {
        if (!desplegable.contains(e.target) && e.target !== btnSede) {
            desplegable.style.display = "none";
        }
    });

    var btnMadrid = document.getElementById("btnMadrid");
    var btnBarcelona = document.getElementById("btnBarcelona");
    var sedeSeleccionada = document.getElementById("sedeSeleccionada");
    var columnaIzquierda = document.querySelector(".columna.izquierda");
    var columnaDerecha = document.querySelector(".columna.derecha");
    var columnaCentral = document.querySelector(".columna.central");

    btnMadrid.addEventListener("click", function() {
        mostrarSedeSeleccionada("Madrid");
        columnaIzquierda.classList.add('visible');
        columnaDerecha.classList.add('visible');
        columnaCentral.classList.remove('visible');
        columnaIzquierda.classList.remove('novisible');
        columnaDerecha.classList.remove('novisible');
        columnaCentral.classList.remove('novisible');
        var nuevaImagen = 'fondoMadrid2.jpg';
        document.body.style.backgroundImage = 'url(./img/' + nuevaImagen + ')';
    });

    btnBarcelona.addEventListener("click", function() {
        mostrarSedeSeleccionada("Barcelona");
        columnaIzquierda.classList.add('novisible');
        columnaDerecha.classList.add('novisible');
        columnaCentral.classList.add('novisible');

        var nuevaImagen = 'Barcelona.jpg';
        document.body.style.backgroundImage = 'url(./img/' + nuevaImagen + ')';
    });

    //mostrar estadistica al pulsar un boton de estadistica

    var descripcionElemento = document.querySelector(".descripcion");
    var tituloEst = document.querySelector(".tituloCentral");

    botonEstadistica1.addEventListener("click", function(){
        columnaCentral.classList.add('visible');
        grafica1();
        tituloEst.textContent = "Temperatura media del ultimo mes";
        descripcionElemento.textContent = "Esta grafica contiene los datos sobre la temperatura media diaria del ultimo mes. Los datos recogidos ayudan a visualizar los picos de temperatura, permitiendo establecer que dias aumenta la temperatura, normalmente por el transito de coches.";
        
    });

    botonEstadistica2.addEventListener("click", function(){
        columnaCentral.classList.add('visible');
    });

    botonEstadistica3.addEventListener("click", function(){
        columnaCentral.classList.add('visible');
    });

    botonEstadistica4.addEventListener("click", function(){
        columnaCentral.classList.add('visible');
    });

    botonEstadistica5.addEventListener("click", function(){
        columnaCentral.classList.add('visible');
    });

    botonEstadistica6.addEventListener("click", function(){
        columnaCentral.classList.add('visible');
    });


	//actualizar cada 5 segundos
	setInterval(function() {
		//actualiza valores actuales
 		
        
        actualizarDatosHumedad();
        actualizarDatosTemperatura();
        actualizarDatosHumo();
        actualizarDatosMatriculas();
    }, 5000);

});

function actualizarDatosHumo(){
	console.log("datos actuales actualizando.");
    var idParking= 1;
    var idCiudad = 1;
    var idTipo= 3; //humedad
    $.ajax({
        
        data: { idCiudad : idCiudad,idParking : idParking, idTipo : idTipo}, 
        url: './GetActualGases',  
        dataType: 'json',
        type: 'post',
        
        success: function(data) {
            console.log("Respuesta del servidor:", data);
            
            
            var valorNuevo = data.map(item => item.value);
            console.log("Datos cargados correctamente:", valorNuevo);
			var sensorTemperaturaElemento = document.getElementById('sensorHumo');
			sensorTemperaturaElemento.textContent = 'Sensor de Humo: ' + valorNuevo + ' ppm';
            
        },
        error: function(xhr, status, error) {
            console.error("Error al cargar el archivo JSON:", error);
        }
    });
}


function actualizarDatosHumedad(){
	console.log("datos actuales actualizando.");
    var idParking= 1;
    var idCiudad = 1;
    var idTipo= 2; //humedad
    $.ajax({
        
        data: { idCiudad : idCiudad,idParking : idParking, idTipo : idTipo}, 
        url: './GetActualTemp',  
        dataType: 'json',
        type: 'post',
        
        success: function(data) {
            console.log("Respuesta del servidor:", data);
            
            
            var valorNuevo = data.map(item => item.value);
            console.log("Datos cargados correctamente:", valorNuevo);
			var sensorTemperaturaElemento = document.getElementById('sensorHumedad');
			sensorTemperaturaElemento.textContent = 'Sensor de Humedad: ' + valorNuevo + ' %';
            
        },
        error: function(xhr, status, error) {
            console.error("Error al cargar el archivo JSON:", error);
        }
    });
}


function actualizarDatosMatriculas(){
	console.log("datos actuales actualizando.");
    var idParking= 1;
    var idCiudad = 1;
    $.ajax({
        
        data: { idCiudad : idCiudad,idParking : idParking}, 
        url: './GetActualCarHistory',  
        dataType: 'json',
        type: 'post',
        
        success: function(data) {
            console.log("Respuesta del servidor:", data);
            
            
            var valorNuevo = data.map(item => item.matricula);
            var areaMatriculas = document.getElementById('areaMatriculas');
			var matriculas = document.getElementById('areaMatriculas');
			matriculas.textContent =   valorNuevo ;
            
        },
        error: function(xhr, status, error) {
            console.error("Error al cargar el archivo JSON:", error);
        }
    });
}

function actualizarDatosTemperatura(){
	console.log("datos actuales actualizando.");
    var idParking= 1;
    var idCiudad = 1;
    var idTipo= 1; //temperatura
    $.ajax({
        
        data: {idTipo : idTipo, idCiudad : idCiudad, idParking : idParking}, 
        url: './GetActualTemp',  
        dataType: 'json',
        type: 'post',
        
        success: function(data) {
            console.log("Respuesta del servidor:", data);
            
            
            var valorNuevo = data.map(item => item.value);
            console.log("Datos cargados correctamente:", valorNuevo);
			var sensorTemperaturaElemento = document.getElementById('sensorTemperatura');
			sensorTemperaturaElemento.textContent = 'Sensor de Temperatura: ' + valorNuevo + ' ºC';
            
        },
        error: function(xhr, status, error) {
            console.error("Error al cargar el archivo JSON:", error);
        }
    });
}



function grafica1(){
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
}

function actualizarFechaHora() {
    var fechaHoraActual = new Date();
    var formatoFechaHora = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric', hour24: true };
    var fechaHoraTexto = fechaHoraActual.toLocaleDateString('es-ES', formatoFechaHora);

    document.getElementById("fechaHora").textContent = fechaHoraTexto;
}

function mostrarSedeSeleccionada(sede) {
    sedeSeleccionada.innerHTML =  sede;
    sedeSeleccionada.style.display = "block";
}

