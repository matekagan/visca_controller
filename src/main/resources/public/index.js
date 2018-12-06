function executeCommand(command) {
    let params = {
        dest: getDestination(),
        ...getParams()
    }
    console.log(params);
    let url = buildUrl(command, params);
    fetch(url, { method: 'POST' })
        .then(response => response.text())
        .then(response => {
            document.getElementById('response-div').innerHTML = response;
        });
}

function getDestination() {
    if (document.getElementById('all-checkbox').checked){
        return 'all';
    }
    return document.getElementById('destination-input').value;
}

function getParams() {
    return {
        pan_speed: document.getElementById('pan-speed').value || 7,
        tilt_speed: document.getElementById('tilt-speed').value || 7,
        zoom_speed: document.getElementById('zoom-speed').value || 4
    }
}

function validateInput(e){
    if(parseInt(e.target.value) < 1){
        e.target.value = 1;
    } else if (parseInt(e.target.value) > 16) {
        e.target.value = 16;
    }
}

function validateZoom(e){
    if(parseInt(e.target.value) < 2){
        e.target.value = 2;
    } else if (parseInt(e.target.value) > 7) {
        e.target.value = 7;
    }
}

function setInputDisabled() {
    document.getElementById('destination-input').disabled = document.getElementById('all-checkbox').checked
}

function getParameters() {
    return document.getElementById('parameter-input').value;
}

function buildUrl(command, params) {
    const url = new URL(window.location.origin + '/command');
    url.searchParams.append('cmd', command);
    Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));
    return url;
}