// array of song's id that need add to library
addSongArray = []
// array of song's id that need remove from library
deleteSongArray = []
// array of album's id that need add
addAlbumArray = []
// array of album's id that need delete
deleteAlbumArray = []


function changeL (lang) {
    location.href =  location.href.split('?')[0] + '?lang=' + lang;
}


// Function use in all page where are buttons to add and delete songs or albums except library
function addSong(elem, addText, deleteText, id, isAuthorise, userLim, songLim) {
    if (!isAuthorise) {
        var element = document.getElementById("sign-authorization");
        element.style.display = "block";
        setTimeout(function () {
            element.style.display = "none";
        }, 3000);
        return;
    }

    if (userLim != -1 && userLim < songLim) {
        // console.log("into if that set timeout")
        var elemValid = document.getElementById("sign-invalid-music");
        elemValid.style.display = "block";
        setTimeout(function () {
            elemValid.style.display = "none";
        }, 3000);
        return;
    }

    let index;
    name = elem.className;
    if (name == "btn btn-outline btn-error") {
        index = addSongArray.findIndex(element => element == id);
        if (index > -1) {addSongArray.splice(index, 1);}
        deleteSongArray.push(id);

        elem.className = "btn btn-outline btn-success";
        elem.textContent = addText;
    } else {
        index = deleteSongArray.findIndex(element => element == id);
        if (index > -1) {deleteSongArray.splice(index, 1);}
        addSongArray.push(id);

        elem.className = "btn btn-outline btn-error";
        elem.textContent = deleteText;
    }
    console.log("add array " + addSongArray)
    console.log("delete array " + deleteSongArray)
}



// function to add and delete songs from library
function addSongLibrary(elem, addText, deleteText, id) {
    name = elem.className;
    let index;

    if (name == "btn btn-outline btn-error") {
        index = addSongArray.findIndex(element => element == id);
        if (index > -1) {addSongArray.splice(index, 1);}
        deleteSongArray.push(id);

        elem.className = "btn btn-outline btn-success";
        elem.textContent = addText;
    } else {
        index = deleteSongArray.findIndex(element => element == id);
        if (index > -1) {deleteSongArray.splice(index, 1);}
        addSongArray.push(id);

        elem.className = "btn btn-outline btn-error";
        elem.textContent = deleteText;
    }
    console.log("add array " + addSongArray)
    console.log("delete array " + deleteSongArray)
}



// function add end delete album from user's library on albums page (several album)
function addAlbum(elem, addText, deleteText, id, isAuthorise, userLim, albumLim) {
    if (!isAuthorise) {
        var element = document.getElementById("sign-authorization");
        element.style.display = "block";
        setTimeout(function () {
            element.style.display = "none";
        }, 3000);
        return;
    }

    if (userLim != -1 && userLim < albumLim) {
        var elemValid = document.getElementById("sign-invalid-album");
        elemValid.style.display = "block";
        setTimeout(function () {
            elemValid.style.display = "none";
        }, 3000);
        return;
    }

    name = elem.className;
    let index;
    // console.log("count of buttons = " + songsBtns.length);
    if (name == "btn btn-outline btn-error") {
        index = addAlbumArray.findIndex(element => element == id);
        if (index > -1) {addAlbumArray.splice(index, 1);}
        deleteAlbumArray.push(id);

        elem.className = "btn btn-outline btn-success";
        elem.textContent = addText;
    } else {
        index = deleteAlbumArray.findIndex(element => element == id);
        if (index > -1) {deleteAlbumArray.splice(index, 1);}
        addAlbumArray.push(id);

        elem.className = "btn btn-outline btn-error";
        elem.textContent = deleteText;
    }
    console.log("add array " + addAlbumArray);
    console.log("delete array " + deleteAlbumArray);
}



// function add end delete album from user's library on albums page (single album)
function addSingleAlbum(elem, addText, deleteText, id, isAuthorise, userLim, albumLim) {
    if (!isAuthorise) {
        var element = document.getElementById("sign-authorization");
        element.style.display = "block";
        setTimeout(function () {
            element.style.display = "none";
        }, 3000);
        return;
    }

    if (userLim != -1 && userLim < albumLim) {
        var elemValid = document.getElementById("sign-invalid-album");
        elemValid.style.display = "block";
        setTimeout(function () {
            elemValid.style.display = "none";
        }, 3000);
        return;
    }

    name = elem.className;
    songsBtns = document.getElementsByClassName('song-btn');
    // console.log("count of buttons = " + songsBtns.length);
    if (name == "album-btn btn btn-outline btn-error") {

        addSongArray = [];
        deleteSongArray = [];
        addAlbumArray = [];
        for (i = 0; i < songsBtns.length; ++i) {
            songsBtns[i].className = "song-btn btn btn-outline btn-success";
            songsBtns[i].textContent = addText;
        }
        deleteAlbumArray.push(id);

        elem.className = "album-btn btn btn-outline btn-success";
        elem.textContent = addText;
    } else {
        addSongArray = [];
        deleteSongArray = [];
        deleteAlbumArray = [];
        for (i = 0; i < songsBtns.length; ++i) {
            songsBtns[i].className = "song-btn btn btn-outline btn-error";
            songsBtns[i].textContent = deleteText;
        }
        addAlbumArray.push(id);

        elem.className = "album-btn btn btn-outline btn-error";
        elem.textContent = deleteText;
    }
    // console.log("add array " + addAlbumArray);
    // console.log("delete array " + deleteAlbumArray);
}



// function add end delete songs of album from user's library on albums page (single album)
function addSongSingleAlbum(elem, addText, deleteText, id, isAuthorise, userLim, songLim) {

    if (!isAuthorise) {
        var element = document.getElementById("sign-authorization");
        element.style.display = "block";
        setTimeout(function () {
            element.style.display = "none";
        }, 3000);
        return;
    }

    if (userLim != -1 && userLim < songLim) {
        var elemValid = document.getElementById("sign-invalid-music");
        elemValid.style.display = "block";
        setTimeout(function () {
            elemValid.style.display = "none";
        }, 3000);
        return;
    }

    let index;
    name = elem.className;
    const countAll = document.getElementsByClassName('song-btn').length;
    let countAdded = document.getElementsByClassName('song-btn btn btn-outline btn-error').length;
    console.log("added = " + countAdded);
    if (name == "song-btn btn btn-outline btn-error") {
        index = addSongArray.findIndex(element => element == id);
        if (index > -1) {addSongArray.splice(index, 1);}
        deleteSongArray.push(id);

        elem.className = " song-btn btn btn-outline btn-success";
        elem.textContent = addText;
        console.log("minus")
        countAdded--;
    } else {
        index = deleteSongArray.findIndex(element => element == id);
        if (index > -1) {deleteSongArray.splice(index, 1);}
        addSongArray.push(id);

        elem.className = "song-btn btn btn-outline btn-error";
        elem.textContent = deleteText;
        countAdded++;
    }

    albumButt = document.getElementsByClassName('album-btn');
    if (countAll > addSongArray.length && albumButt[0].className == "album-btn btn btn-outline btn-error") {
        albumButt[0].className = "album-btn btn btn-outline btn-success"
        albumButt[0].textContent = addText;
        addAlbumArray = [];
        deleteAlbumArray = [];
    }
    if (countAll === countAdded && albumButt[0].className == "album-btn btn btn-outline btn-success") {
        albumButt[0].className = "album-btn btn btn-outline btn-error"
        albumButt[0].textContent = deleteText;
        deleteAlbumArray = [];
        addAlbumArray = [];
    }
    console.log("add song array " + addSongArray);
    console.log("delete song array " + deleteSongArray);
    console.log("add album array " + addAlbumArray);
    console.log("delete album array " + deleteAlbumArray);
}







// function send arrays of song's and album's id to add music in db through batch update
function sendData(realUrl, authenticate) {
    console.log(authenticate);

    if (!authenticate || (addSongArray.length === 0 && deleteSongArray.length === 0 &&
        addAlbumArray.length === 0 && deleteAlbumArray.length === 0)) {
        window.location.href = realUrl;
        return;
    }

    let json = JSON.stringify({
        "songsADD": addSongArray,
        "songsDELETE": deleteSongArray,
        "albumsADD": addAlbumArray,
        "albumsDELETE": deleteAlbumArray,
    });
    // console.log(json);
    fetch('/speaker/update/music', {
            method: 'POST',
            headers: {
                "content-type": "application/json"
            },
            body: JSON.stringify({
                "songsADD": addSongArray,
                "songsDELETE": deleteSongArray,
                "albumsADD": addAlbumArray,
                "albumsDELETE": deleteAlbumArray,
            })
        }
    )
        .then(r => {
            console.log("Completed with result:", r);
            addSongArray = [];
            deleteSongArray = [];
            addAlbumArray = [];
            deleteAlbumArray = [];
            window.location.href = realUrl;
    })
        .catch(error => {console.error(error);})
};



// function send arrays of song's and album's id to add music in db through batch update but from library
function sendDataLibrary(realUrl) {
    if (addSongArray.length === 0 && deleteSongArray.length === 0 &&
        addAlbumArray.length === 0 && deleteAlbumArray.length === 0) {
        window.location.href = realUrl;
        return;
    }

    let json = JSON.stringify({
        "songsADD": addSongArray,
        "songsDELETE": deleteSongArray,
        "albumsADD": addAlbumArray,
        "albumsDELETE": deleteAlbumArray,
    });
    // console.log(json);
    fetch('/speaker/update/music', {
            method: 'POST',
            headers: {
                "content-type": "application/json"
            },
            body: JSON.stringify({
                "songsADD": addSongArray,
                "songsDELETE": deleteSongArray,
                "albumsADD": addAlbumArray,
                "albumsDELETE": deleteAlbumArray,
            })
        }
    )
        .then(r => {
            console.log("Completed with result:", r);
            addSongArray = [];
            deleteSongArray = [];
            addAlbumArray = [];
            deleteAlbumArray = [];
            window.location.href = realUrl;
        })
        .catch(error => {console.error(error);})
};


// function send data to update db before submit form
function sendDataForm(authenticate) {
    console.log(authenticate);
    if (!authenticate || (addSongArray.length === 0 && deleteSongArray.length === 0 &&
        addAlbumArray.length === 0 && deleteAlbumArray.length === 0)) {
        return;
    }
    let json = JSON.stringify({
        "songsADD": addSongArray,
        "songsDELETE": deleteSongArray,
        "albumsADD": addAlbumArray,
        "albumsDELETE": deleteAlbumArray,
    });
    fetch('/speaker/update/music', {
            method: 'POST',
            headers: {
                "content-type": "application/json"
            },
            body: json
        }
    )
        .then(r => {
            console.log("Completed with result:", r);
            addSongArray = [];
            deleteSongArray = [];
            addAlbumArray = [];
            deleteAlbumArray = [];
        })
        .catch(error => {console.error(error);})
};


// function send data to update db before submit form
function sendDataLibraryForm() {
    if (addSongArray.length === 0 && deleteSongArray.length === 0 &&
        addAlbumArray.length === 0 && deleteAlbumArray.length === 0) {
        return;
    }
    let json = JSON.stringify({
        "songsADD": addSongArray,
        "songsDELETE": deleteSongArray,
        "albumsADD": addAlbumArray,
        "albumsDELETE": deleteAlbumArray,
    });
    // console.log(json);
    fetch('/speaker/update/music', {
            method: 'POST',
            headers: {
                "content-type": "application/json"
            },
            body: json
        }
    )
        .then(r => {
            console.log("Completed with result:", r);
            addSongArray = [];
            deleteSongArray = [];
            addAlbumArray = [];
            deleteAlbumArray = [];
        })
        .catch(error => {console.error(error);})
}






function sendDataLogoutForm(authenticate, username) {
    if (!authenticate || (addSongArray.length === 0 && deleteSongArray.length === 0 &&
        addAlbumArray.length === 0 && deleteAlbumArray.length === 0)) {
        return;
    }
    let json = JSON.stringify({
        "songsADD": addSongArray,
        "songsDELETE": deleteSongArray,
        "albumsADD": addAlbumArray,
        "albumsDELETE": deleteAlbumArray,
        "username": username
    });
    fetch('/speaker/update/logout/', {
            method: 'POST',
            headers: {
                "content-type": "application/json"
            },
            body: json
        }
    )
        .then(r => {
            console.log("Completed with result:", r);
            addSongArray = [];
            deleteSongArray = [];
            addAlbumArray = [];
            deleteAlbumArray = [];
            username = null
            fetch('speaker/logout', {method: 'POST'})
                .then(responce => {
                    console.log("Complete " + responce.status);
                })
        })
        .catch(error => {console.error(error);})
};




// // doesn't work
// // should confirmate user then if he will change his limitation it cat delete song that lim value is higher then his self
// function confirmation(event, userLim) {
//     choeseLim = document.getElementById("select-item");
//     if (userLim > choeseLim.value) {
//         if (!confirm("Do you want save changes?")) {
//             e.preventDefault();
//             return;
//         }
//     }
// }