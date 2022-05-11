
var desarrolladorSelect = document.getElementById('desarrolladorSelect')
var proyectoSelect = document.getElementById('proyecto')
var panelGraficas =  document.getElementById('panelgraficas')
panelGraficas.classList.add('d-none')
proyectoSelect.addEventListener('change', function (evt) {
    evt.preventDefault()
    panelGraficas.classList.remove('d-block')
    let eventoActual = evt.target
    let itemSeleccionado = eventoActual.options[this.selectedIndex]
    mostrarComboDesarrolladores(itemSeleccionado.value)
})

desarrolladorSelect.addEventListener('change', function (evt) {
    evt.preventDefault()
    let codDesarrollador = evt.target.options[this.selectedIndex].value
    let idProyecto = $("#proyecto").val();
    reporteXProyecto(codDesarrollador, idProyecto)
    panelGraficas.classList.add('d-block')
})

function reporteXProyecto(codDesarrollador, idProyecto) {
    var idProyecto = idProyecto;
    if (idProyecto.length > 0) {
        $.ajax({
            url: '/EstimacionesAdmin/reporteChart/' + idProyecto + '/' + codDesarrollador,
            type: 'GET',
            data: null,
            processData: false,
            contentType: "application/json",
            beforeSend: function () {
                $.LoadingOverlay("show");
            }
        }).done(function (data, textStatus, jqXHR) {
            $.LoadingOverlay("hide");
            Swal.fire(
                'Ok!',
                data.mensaje,
                'success'
            );
            for (var i = 0; data.data.length > i; i++) {
                morrisDonutData(data.data[i].element, data.data[i].data, data.data[i].colors);
            }
        }).fail(function (jqXHR, textStatus, errorThrown) {
            $.LoadingOverlay("hide");
            Swal.fire(
                'Oops!',
                jqXHR.responseJSON.mensaje,
                'error'
            );
        });
    }
}

function mostrarComboDesarrolladores(codProyecto) {

    let desarrolladorBox = document.getElementById('desarrolladorBox');
    $.ajax({
        url: '/Estructuraorganizacional/getEstructuraXCodProyecto/' + codProyecto,
        type: 'GET',
        processData: false,
        contentType: "application/json",
        beforeSend: function () {
            $.LoadingOverlay("show");
        }
    }).done(function (data, textStatus, jqXHR) {
        $("#desarrolladorSelect").find('option').remove().end();
        let opcSeleccionar = document.createElement('option')
        opcSeleccionar.text = 'Seleccione una opcion'
        opcSeleccionar.value = 0
        desarrolladorSelect.appendChild(opcSeleccionar)
        Array.prototype.forEach.call(data, function (elemento, key) {
            let opcion = document.createElement('option');
            opcion.value = elemento.id;
            opcion.text = elemento.nombre;
            opcion.dataset.codpadre = elemento.codpadre;
            desarrolladorSelect.appendChild(opcion);
            $("#desarrolladorSelect").selectpicker("refresh");
        });
        $.LoadingOverlay("hide");

    }).fail(function (jqXHR, textStatus, errorThrown) {
        $.LoadingOverlay("hide");
        Swal.fire(
            'Oops!',
            jqXHR.responseJSON.mensaje,
            'error'
        );
    });
    desarrolladorBox.classList.add('d-block')
}

