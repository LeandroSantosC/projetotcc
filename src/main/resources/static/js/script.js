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
const tab_button = document.getElementById("tab-button")
const tab_board = document.getElementById("tab-board")
const edit_switch = document.getElementById("edit-switch")
const add_button = document.getElementById("add-button")
const card_options = document.getElementsByClassName("card-options")
const content = document.getElementById('content')
const form_button_edit = document.getElementById('form-button-edit');
const buttons = main_buttons.querySelectorAll(".buttons"); // Ajuste o seletor para os seus botões/cards
const searchInput = document.querySelector("#search-bar input");
let searchValue = "";
let categorySelected = "";
let isEditOn = false;
let initialData;
let currentData;
let board = []

// window.speechSynthesis.addEventListener("voiceschanged", () => {
//     let voicesList = window.speechSynthesis.getVoices()
//     let optionEl = document.createElement("option")
//     optionEl.setAttribute("value", i)
//     optionEl.innerText = voicesList[i].name;
// })
const input = document.getElementById('input-btn-name');
const errorMessage = document.getElementById('error-message');

input.addEventListener('input', () => {
    // Remove caracteres especiais em tempo real
    if(/[A-ZÀ-ÚÇ]*$/.test(input.value)){
        input.value = input.value.toLowerCase();
        if (/[a-zà-ú-ç!?\s]*$/.test(input.value)) {
            input.value = input.value.replace(/[^a-zà-ú-ç!?\s]/g, ''); // Remove caracteres inválidos
        }
    }
});

document.addEventListener('DOMContentLoaded', function() {
    // Código a ser executado quando a página for carregada
    let invisibles = content.querySelectorAll('.invisible')
        invisibles.forEach(element => {
            element.style.display = "none"
    })

    sortDivsByOrder();
});

function searchButtons() {
    buttons.forEach(button => {
        // Verifica se o botão corresponde ao valor de busca
        const matchesSearch = button.querySelector('span:first-child').textContent?.includes(searchValue);

        // Verifica se o botão pertence à categoria selecionada
        const matchesCategory = button.dataset.category?.includes(categorySelected);

        // Verifica se o botão está invisível
        const isVisible = !button.classList.contains('invisible');

        console.log(button.name + ": " + matchesSearch, matchesCategory, isVisible);
        // Aplica a lógica final
        if (matchesSearch && matchesCategory && (isEditOn || isVisible)) {
            button.style.display = "flex"; // Exibe o botão
        } else {
            button.style.display = "none"; // Oculta o botão
        }
    });
}


function sortDivsByOrder() {
    const boxes = Array.from(main_buttons.children); // Seleciona todas as divs dentro do container
    
    // Ordena as divs pela propriedade 'order'
    boxes.sort((a, b) => {
        return parseInt(a.style.order || 0) - parseInt(b.style.order || 0); // Ordena pela propriedade order
    });

    // Reorganiza o DOM de acordo com a ordem
    boxes.forEach(box => main_buttons.appendChild(box)); // Reinsere as divs ordenadas no container
}

main_buttons.addEventListener("click", function(event) {
    // Processar clique em botão de conteúdo
    let buttonClicked = event.target.closest(".btn-content");
    if (buttonClicked) {
        handleButtonClick(buttonClicked);
    }

    // Processar clique em botão de mostrar/ocultar cartão
    let show_card_btn = event.target.closest(".show-card-btn");
    if (show_card_btn) {
        toggleCardVisibility(show_card_btn);
    }

    // Processar clique em botão de deletar cartão
    let del_card_btn = event.target.closest(".del-card-btn");
    if (del_card_btn) {
        handleDeleteCard(del_card_btn);
    }
});

function handleButtonClick(buttonClicked) {
    let ut = new SpeechSynthesisUtterance(buttonClicked.textContent);
    if (window.speechSynthesis.speaking) {
        window.speechSynthesis.cancel();
    }
    window.speechSynthesis.speak(ut);
    addButtonToBoard(buttonClicked);
}

function toggleCardVisibility(show_card_btn) {
    let button = show_card_btn.parentElement.parentElement;
    let icon = show_card_btn.querySelector('span');
    let input = show_card_btn.querySelector("input[type='checkbox']");

    if (input && input.checked) {
        button.classList.remove('invisible');
        icon.classList.add('fa-eye');
        icon.classList.remove('fa-eye-slash');
    } else {
        button.classList.add('invisible');
        icon.classList.remove('fa-eye');
        icon.classList.add('fa-eye-slash');
    }

    attOrder();
}

function handleDeleteCard(del_card_btn) {
    let button = del_card_btn.parentElement.parentElement.id;
    deletar(button);
}



const btn_img = document.getElementById("btn-img");
const url_img = document.getElementById("btn-url-img");
const input_img = document.getElementById("input-btn-img");
const input_img_url = document.getElementById("input-url-img");
const confirm_img_input = document.getElementById("confirm-img-input");
const del_img_input = document.getElementById("del-img-input");

del_img_input.addEventListener("click", () => {
    input_img_url.classList.add("disabled");
    input_img_url.querySelector("input").value = "";
})

const img_url = document.getElementById('imageUrl');

confirm_img_input.addEventListener("click", () => {
    const input = input_img_url.querySelector("input");
    const img = form_button_edit.querySelector('img');
    

    checkIfImage(input.value).then( isImage => {
        if(isImage){
            img.src = input.value;
            img_url.value = input.value;
            input.value = "";
            input_img_url.classList.add("disabled");
            attCurrentData();
        }
    })
})

function checkIfImage(url) {
    return fetch(url)
        .then(response => {
            // Verifica se o status da resposta é 2xx (sucesso) e se o tipo de conteúdo é uma imagem
            const contentType = response.headers.get('Content-Type');
            if (contentType && contentType.startsWith('image/')) {
                return true;
            } else {
                return false;
            }
        })
        .catch(error => {
            console.error("Erro ao verificar a URL:", error);
            return false;  // Em caso de erro, como a URL não ser acessível, retorna falso
        });
}

url_img.addEventListener("click", () => {
    input_img_url.classList.toggle("disabled");
})

btn_img.addEventListener("click", () => {
    input_img.click();
})

input_img.addEventListener("change", (event) => {
    const file = event.target.files[0];

    if(file && file.type.startsWith('image/')){
        const imageURL = URL.createObjectURL(file); // Gera uma URL local temporária
        form_button_edit.querySelector('img').src = imageURL; // Define o src da imagem com a URL gerada
    }
})

const generate_image = document.getElementById("generate-img");
const input_btn_name = document.getElementById("input-btn-name");

generate_image.addEventListener("click", () => {
    form_button_edit.querySelector('img').src = "https://cdn.pixabay.com/animation/2023/08/11/21/18/21-18-05-265_512.gif";
    scrap(input_btn_name.value);
})

function scrap(buttonName){
    fetch('/scrap', {
        method: 'POST', // Método HTTP
        headers: {
          'Content-Type': 'text/plain' // Indicando que os dados são no formato JSON
        },
        body: buttonName // Convertendo o objeto para uma string JSON
      })
      .then(response => response.text())
      .then(data => {
        form_button_edit.querySelector('img').src = data;
        img_url.value = data;
        attCurrentData();
      })
      .catch((error) => {
        console.error("Error:", error); // Manipule qualquer erro
      });
}




const animated_btns = document.getElementsByClassName("animated-btn")

Array.from(animated_btns).forEach(animated_btn =>{
    animated_btn.addEventListener("click", () => {
        animated_btn.classList.toggle('active')
    })
})

const add_category = document.getElementById("add-category");
const category_edit = document.getElementById("category-edit");
const input_category = document.getElementById("input-category");
const confirm_category = document.getElementById("confirm-category");

add_category.addEventListener("click", (event) =>{
    if(!add_category.classList.contains('del-category')){
        add_category.classList.add("del-category");
    }
    else{
        add_category.classList.remove('del-category');
    }
})

add_category.addEventListener("transitionend", (e) => {
    if (e.target === add_category) {
        if(add_category.classList.contains("del-category")){
            input_category.style.display = "";
            category_edit.style.display = "none";
        }
        else{
            input_category.style.display = "none";
            input_category.value = "";
            category_edit.style.display = "";
        }
    }
});

confirm_category.addEventListener("click", (event) => {
    if(input_category.value.length > 0){
        const options = category_edit.querySelectorAll("option");
        let isNewOption = true;

        options.forEach(option => {
            if(option.textContent === input_category.value){
                option.selected = true;
                isNewOption = false;
            }
        })

        if(isNewOption){
            category_edit.add(new Option(input_category.value, null, false, true));
        }

        add_category.classList.remove('del-category');
    }
})

function editButton(buttonId){
    let button = document.getElementById(buttonId);
    initialData = new FormData();
    let id = "";
    let name = "";
    let img = "/images/Capturar.PNG";
    let category = "";
    let categoryName = "";

    if(button){
        id = button.id;
        name = button.querySelector('span').textContent;
        img = button.querySelector('img').src;
        category = button.dataset.category;
        categoryName = category_selector.querySelector(`option[value="${category}"]`)?.textContent;
    }

    initialData.append('id', id)
    initialData.append('name', name);
    initialData.append('image', img);
    initialData.append('category', categoryName);
    currentData = initialData;

    form_button_edit.querySelector('#imageUrl').value = initialData.get('image');
    form_button_edit.querySelector('img').src = initialData.get('image');
    form_button_edit.querySelector('#input-btn-id').value = initialData.get('id');
    form_button_edit.querySelector('#input-btn-name').value = initialData.get('name');
    form_button_edit.querySelector('#category-edit').value = category;
    form_button_edit.parentElement.style.display = "flex";
}

// Detectar mudanças no formulário
form_button_edit.addEventListener("change", () => {
    attCurrentData();
});

function attCurrentData(){
    currentData = new FormData(form_button_edit);
    let category = category_edit.options[category_edit.selectedIndex].textContent;
    currentData.set('category', category);
}

// Comparar dois objetos FormData
function areFormsEqual(data1, data2) {
    if (data1.entries().length !== data2.entries().length) return false;
    for (let [key, value] of data1.entries()) {
        if (data2.get(key) !== value) return false;
    }
    return true;
}


function fecharForm(event){
    if (!areFormsEqual(initialData, currentData)) {
        event.preventDefault();
         // Requerido para exibir o alerta
        if(confirm("Deseja descartar as alterações?")){
            form_button_edit.parentElement.style.display = "";
            console.log(currentData.get('id'));
            console.log(currentData.get('name'));
            console.log(currentData.get('image'));
            console.log(currentData.get('category'));
            currentData = "";
        }
    }
    else{
        form_button_edit.parentElement.style.display = "";
    }
}

form_button_edit.addEventListener('submit', async (event) => {
    event.preventDefault();

    saveButton(currentData);
});
  

async function saveButton(buttonForm){
    const formData = Object.fromEntries(buttonForm); // Converte os dados do formulário em um objeto

    console.log(formData);

    try {
        const response = await fetch(`/${formData.id}`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData),
        });

        if (response.ok) {
            console.log('Formulário enviado');
            initialData = buttonForm;
            location.reload();
        } else {
            console.error('Erro ao enviar o formulário');
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
    }
}

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
    categorySelected = event.target.value;
    searchButtons();
});

tab_button.addEventListener("click", function(){
    main_buttons.style.display = "flex"
    main_boards.style.display = "none"
})

tab_board.addEventListener("click", function(){
    main_buttons.style.display = "none"
    main_boards.style.display = "flex"
})

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
        phrase.push(word.textContent);
    }
    if(window.speechSynthesis.speaking){
        window.speechSynthesis.cancel();
    }
    window.speechSynthesis.speak(new SpeechSynthesisUtterance(phrase));

})

menu_button.addEventListener("click", function(){
    sendChangesOnLogout();
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

// -------------------------SWITCH E CARD OPTIONS----------------------------------------

edit_switch.addEventListener('change', (event) => {

    isEditOn = event.target.checked;

    if (event.target.checked){
        add_button.style.display = "flex";
        Array.from(card_options).forEach(element => {
            element.style.display = "flex";
            element.parentElement.classList.add('editable');
            element.parentElement.setAttribute('draggable', true);
        })
    }
    else{
        add_button.style.display = "none";

        Array.from(card_options).forEach(element => {
            element.style.display = "none";
            element.parentElement.classList.remove('editable');
            element.parentElement.setAttribute('draggable', false);
        })
    }

    searchButtons();
})

add_button.addEventListener("click", () => {
    editButton();
})

// btn_content.addEventListener('touchstart', function(event) {
//     event.preventDefault(); // Impede o arrasto no mobile
// });

// btn_content.addEventListener('touchmove', function(event) {
//     event.preventDefault(); // Impede o movimento de arrasto
// });


main_buttons.addEventListener('dragstart', (e) => {
    e.target.classList.add('dragging');
})

// main_buttons.addEventListener('touchstart', (e) => {
//     e.target.classList.add('dragging');
// })

main_buttons.addEventListener('dragend', (e) => {
    e.target.classList.remove('dragging');
})

// main_buttons.addEventListener('touchend', (e) => {
//     e.target.classList.remove('dragging');
// })

let isAnimating = false;

main_buttons.addEventListener('dragover', (button) => {
    ordenar(button);
})

// main_buttons.addEventListener('touchover', (button) => {
//     ordenar(button);
// })

function attOrder(){
    let buttons = main_buttons.querySelectorAll('.buttons')
    const dataToSend = [];

    buttons.forEach((button, index) => {
        button.style.order = index + 1;
        
        const visivel = (!button.classList.contains('invisible'));
        // Prepara os dados para envio
        const id = button.id; // Supondo que cada botão tenha um id único
        dataToSend.push({ 
            id: id, 
            position: index + 1, 
            isVisible: visivel
        });
    })

    saveChangeInCache(dataToSend);
}

function saveChangeInCache(changes) {
    let cachedChanges = JSON.parse(localStorage.getItem('buttonChanges')) || [];
    
    changes.forEach(change => {
        const existing = cachedChanges.find(c => c.id === change.id);
        if (existing) {
            existing.position = change.position;
            existing.isVisible = change.isVisible;
        } else {
            cachedChanges.push(change);
        }
    });

    localStorage.setItem('buttonChanges', JSON.stringify(cachedChanges));
    console.log("ALTERAÇÃO NO MEIO" + JSON.stringify(cachedChanges));
}

function sendChangesOnLogout() {
    const cachedChanges = JSON.parse(localStorage.getItem('buttonChanges')) || [];

    return new Promise((resolve, reject) => {
        if (cachedChanges.length > 0) {
            fetch('/update-layout', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(cachedChanges),
            })
            .then(response => {
                if (response.ok) {
                    console.log("Alterações salvas com sucesso!");
                    localStorage.removeItem('buttonChanges');
                    window.location.reload();
                    resolve(); // Finaliza a Promise
                } else {
                    reject("Erro ao salvar alterações."); // Rejeita a Promise
                }
            })
            .catch(error => {
                reject(error); // Rejeita a Promise em caso de erro
            });
        } else {
            resolve(); // Se não houver alterações, a Promise é resolvida imediatamente
        }
    });
}

window.onbeforeunload = function(event) {
    fecharForm(event);
    sendChangesOnLogout();
};

function ordenar(event) {
    event.preventDefault();

    const elementoArrastado = main_buttons.querySelector('.dragging');
    const alvo = event.target.closest('.buttons');

    if(alvo !== isAnimating){
        if (alvo && alvo !== elementoArrastado) {
            // Obtém a posição do elemento arrastado e do alvo
            const buttons = Array.from(main_buttons.children);
            const arrastadoIndex = Array.from(main_buttons.children).indexOf(elementoArrastado);
            const alvoIndex = Array.from(main_buttons.children).indexOf(alvo);

            // Determinar os elementos entre o arrastado e o alvo
            const intermediarios = buttons.slice(
                Math.min(arrastadoIndex, alvoIndex) + 1,
                Math.max(arrastadoIndex, alvoIndex)
            );

            // Verifica a relação entre os índices
            if (arrastadoIndex < alvoIndex) {
                // Se o elemento arrastado está antes do alvo, mova-o depois do alvo
                main_buttons.insertBefore(elementoArrastado, alvo.nextSibling);
                alvo.classList.add("moveright"); // Animação para direita
                intermediarios.forEach((button) => {
                    button.classList.add("moveright");
                    setTimeout(() => {
                        button.classList.remove("moveright");
                    }, 500)
                }); 
                isAnimating = alvo;
            } else {
                // Se o elemento arrastado está depois do alvo, mova-o antes do alvo
                main_buttons.insertBefore(elementoArrastado, alvo);
                alvo.classList.add("moveleft"); // Animação para esquerda
                intermediarios.forEach((button) => {
                    button.classList.add("moveleft");
                    setTimeout(() => {
                        button.classList.remove("moveleft");
                    }, 500)
                });
                isAnimating = alvo;
            }

            alvo.addEventListener("animationend", function onAnimationEnd() {
                alvo.classList.remove("moveright", "moveleft");
                intermediarios.forEach((button) => {
                    button.classList.remove("moveright", "moveleft");
                });
                alvo.removeEventListener("animationend", onAnimationEnd); // Remove o evento para evitar duplicação
                isAnimating = false;
            });

        }
    }

    attOrder(); // Atualiza as ordens
}

//------------------------------- DELETE BUTTON -----------------------------

function deletar(id) {
    // Exibe a caixa de confirmação
    // Swal.fire({
    //     title: 'Você tem certeza?',
    //     text: "Esta ação não pode ser desfeita!",
    //     icon: 'warning',
    //     showCancelButton: true,
    //     confirmButtonText: 'Sim, excluir!',
    //     cancelButtonText: 'Cancelar'
    // }).then((result) => {
    //     if (result.isConfirmed) {
    //         // Caso confirmado, chama a função para excluir
    //         excluirItem(id);
    //     }
    // });

    const confirmar = confirm("Você tem certeza que deseja excluir este item?");
    
    if (confirmar) {
        // O usuário clicou em "OK", então faça a exclusão (por exemplo, enviando uma requisição para o servidor)
        excluirItem(id);
        attOrder();
    } else {
        // O usuário clicou em "Cancelar", então nada acontece
        console.log("Exclusão cancelada.");
    }
}

function excluirItem(id) {
    fetch(`/${id}`, {
        method: 'DELETE',
    })
    // .then(response => {
    //     if (response.ok) {
    //         Swal.fire(
    //             'Excluído!',
    //             'O item foi excluído com sucesso.',
    //             'success'
    //         );
    //         location.reload(); // Recarrega a página para refletir a exclusão
    //     } else {
    //         Swal.fire(
    //             'Erro!',
    //             'Houve um erro ao excluir o item.',
    //             'error'
    //         );
    //     }
    // })
    // .catch(error => {
    //     console.error("Erro de rede:", error);
    //     Swal.fire(
    //         'Erro!',
    //         'Houve um erro ao processar a requisição.',
    //         'error'
    //     );
    // });
    .then(response => {
        if (response.ok) {
            alert("Item excluído com sucesso!");
            location.reload();  // Recarrega a página para refletir a exclusão
        } else {
            alert("Erro ao excluir o item.");
        }
    })
    .catch(error => {
        console.error("Erro de rede:", error);
    });
}



//------------------------------- SEARCH BAR --------------------------------


// Selecionando os itens que serão filtrados
const del_search = document.getElementById("del-search");

// Lógica de busca em tempo real
searchInput.addEventListener("input", () => {
    searchValue = searchInput.value.toLowerCase();
    del_search.style.display = searchInput.value != "" ? "flex" : "none"

    searchButtons();
});

del_search.addEventListener("click", () => {
    searchValue = "";
    searchInput.value = "";
    del_search.style.display = "none";

    searchButtons();
})

//---------------------------- BUTTON OPTIONS ----------------------------------


