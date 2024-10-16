const menu_button = document.getElementById("menu-button")
const backspace_button = document.getElementById("backspace-button")
const delete_button = document.getElementById("delete-button")
const play_button = document.getElementById("play-button")
const board_favorite = document.getElementById("board-access-button")
const button_container = document.getElementById("button-container")
const board_cards = document.getElementById("board-cards")


let board = []

// window.speechSynthesis.addEventListener("voiceschanged", () => {
//     let voicesList = window.speechSynthesis.getVoices()
//     let optionEl = document.createElement("option")
//     optionEl.setAttribute("value", i)
//     optionEl.innerText = voicesList[i].name;
// })

console.log("test test");
//Pega os cliques no container de botoes
button_container.addEventListener("click", function(event){
    //Devolve o clique só quando clicar em um objeto da classe "buttons", caso contrário retorna null
    let buttonClick = event.target.closest(".buttons");
    if(buttonClick){
        //Cria um novo "ditador" e pede para ele ditar o conteudo do botão clicado
        let ut = new SpeechSynthesisUtterance(buttonClick.textContent);
        console.log(buttonClick.getAttribute("src"));
        //se ele estiver ja estiver ditando e clicar em outro botão, então cancela.
        if(window.speechSynthesis.speaking){
            window.speechSynthesis.cancel();
        }
        window.speechSynthesis.speak(ut);
        addButtonToBoard(buttonClick);
    }
})

function addButtonToBoard(button){
    //clono o botao e coloco numa var
    let card = button.cloneNode(true);
    //mudo a classe para ele ter aparecia que eu escolhi
    card.className = "card-button"
    //adiciono na board
    board_cards.append(card);
}

delete_button.addEventListener("click", function(){
    while(board_cards.firstChild){
        board_cards.removeChild(board_cards.firstChild);
    }
})

backspace_button.addEventListener("click", function(){
    board_cards.removeChild(board_cards.lastChild);
})

board_cards.addEventListener("click", function(event){
    let buttonClick = event.target.closest(".card-button");
    if(buttonClick){
        board_cards.removeChild(buttonClick);
    }
})

play_button.addEventListener("click", function(){
    let phrase = [];
    for(let card of board_cards.children){
        phrase += card.textContent + " ";
        console.log(phrase)
    }
    if(window.speechSynthesis.speaking){
        window.speechSynthesis.cancel();
    }
    window.speechSynthesis.speak(new SpeechSynthesisUtterance(phrase));

})