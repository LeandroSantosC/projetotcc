const menu_button = document.getElementById("menu-button")
const backspace_button = document.getElementById("backspace-button")
const delete_button = document.getElementById("delete-button")
const play_button = document.getElementById("play-button")
const board_favorite = document.getElementById("board-access-button")
const main_buttons = document.getElementById("main-buttons")
const main_boards = document.getElementById("main-boards")
const board_cards = document.getElementById("board-cards")
const menu = document.getElementById("menu")
const close_button = document.getElementsByClassName("close-button")
const background_window = document.getElementsByClassName("background-window")
const category_selector = document.getElementById("category-selector")

let board = []

// window.speechSynthesis.addEventListener("voiceschanged", () => {
//     let voicesList = window.speechSynthesis.getVoices()
//     let optionEl = document.createElement("option")
//     optionEl.setAttribute("value", i)
//     optionEl.innerText = voicesList[i].name;
// })

console.log("test test");


// function loadHTML(elementId, fileName) {
//     fetch(fileName)
//         .then(response => {
//             if (!response.ok) {
//                 throw new Error('Erro ao carregar o arquivo');
//             }
//             return response.text();
//         })
//         .then(data => {
//             document.getElementById(elementId).innerHTML = data;
            
//         })
//         .catch(error => {
//             console.error('Erro:', error);
//         });
// }

// // Quando o documento carregar, injetar os arquivos HTML
// window.onload = function() {
//     loadHTML('header', 'header.html');
//     loadHTML('board', 'board.html')
//     loadHTML('content', 'button-content.html');
//     loadHTML('menu', 'menu.html');
// }

//Pega os cliques no container de botoes
main_buttons.addEventListener("click", function(event){
    //Devolve o clique só quando clicar em um objeto da classe "buttons", caso contrário retorna null
    let buttonClick = event.target.closest(".buttons");
    if(buttonClick){
        //Cria um novo "ditador" e pede para ele ditar o conteudo do botão clicado
        let ut = new SpeechSynthesisUtterance(buttonClick.textContent);
        //se ele estiver ja estiver ditando e clicar em outro botão, então cancela.
        if(window.speechSynthesis.speaking){
            window.speechSynthesis.cancel();
        }
        window.speechSynthesis.speak(ut);
        addButtonToBoard(buttonClick);
    }
})

main_boards.addEventListener("click", function(event){
    let boardClicked = event.target.closest(".boards");
    if(boardClicked){
        let buttons = boardClicked.querySelectorAll(".buttons");

        buttons.forEach(button => {
            addButtonToBoard(button);
        })
    }
})

category_selector.addEventListener("change", function(event) {
    // Verifica se o valor selecionado é 'tudo'
    if (category_selector.value === "tudo") {
        // Seleciona todos os botões e exibe-os
        let buttons = main_buttons.querySelectorAll(".buttons");
        buttons.forEach(button => {
            button.style.display = "flex"; // Exibe todos os botões
        });
    } else {
        // Seleciona todos os botões e oculta os que não pertencem à categoria selecionada
        let buttons = main_buttons.querySelectorAll(".buttons");

        buttons.forEach(button => {
            console.log(event.target.value)
            console.log(button)
            // Se o botão tem a categoria selecionada, exibe-o, caso contrário oculta
            if (button.classList.contains(event.target.value)) {
                button.style.display = "flex"; // Exibe o botão da categoria
            } else {
                button.style.display = "none"; // Oculta o botão fora da categoria
            }
        });
    }
});


function addButtonToBoard(button){
    //clono o botao e coloco numa var
    let card = button.cloneNode(true);
    //mudo a classe para ele ter aparencia que eu escolhi
    card.className = "buttons card-button"
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
        let word = card.querySelector("span")
        phrase += word.textContent + " ";
        console.log(phrase)
    }
    if(window.speechSynthesis.speaking){
        window.speechSynthesis.cancel();
    }
    window.speechSynthesis.speak(new SpeechSynthesisUtterance(phrase));

})

menu_button.addEventListener("click", function(){
    menu.style.display = "flex"
})

menu.addEventListener("click", function(event){
    if(event.target === menu){
        menu.style.display = "none"
    }
})

Array.from(close_button).forEach(element => {
    element.addEventListener("click", function(event){
        const container = event.target.closest('.background-window');

        if (container) {
            container.style.display = "none";
        }
    })
});


// EFEITOS

// Seleciona o botão
const rippleButtons = document.querySelectorAll('.rippleBtn');

rippleButtons.forEach(rippleButton => {
    rippleButton.addEventListener('click', function (e) {
        // Cria a ondulação
        const ripple = document.createElement('span');
        ripple.classList.add('ripple');
      
        // Pega a posição do clique no botão
        const rect = this.getBoundingClientRect();
        const x = e.clientX - rect.left;
        const y = e.clientY - rect.top;
      
        // Posiciona o efeito ripple
        ripple.style.left = `${x}px`;
        ripple.style.top = `${y}px`;
      
        // Adiciona o ripple no botão
        this.appendChild(ripple);
      
        // Remove o ripple após a animação
        setTimeout(() => {
          ripple.remove();
        }, 600); // O tempo aqui deve ser o mesmo da animação (0.6s)
      });
      
})

