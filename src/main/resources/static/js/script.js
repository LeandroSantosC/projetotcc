const menu_button = document.getElementById("menu-button")
const backspace_button = document.getElementById("backspace-button")
const delete_button = document.getElementById("delete-button")
const play_button = document.getElementById("play-button")
const board_access = document.getElementById("board-access-button")
const button_container = document.getElementById("button-container")

// window.speechSynthesis.addEventListener("voiceschanged", () => {
//     let voicesList = window.speechSynthesis.getVoices()
//     let optionEl = document.createElement("option")
//     optionEl.setAttribute("value", i)
//     optionEl.innerText = voicesList[i].name;
// })

console.log("test test")
//som ao clicar no botao
button_container.addEventListener("click", function(event){
    let buttonClick = event.target.closest(".buttons")
    if(buttonClick){
        let ut = new SpeechSynthesisUtterance(buttonClick.getAttribute("id"))
        if(window.speechSynthesis.speaking){
            window.speechSynthesis.cancel()
        }
        window.speechSynthesis.speak(ut)
    }
})
