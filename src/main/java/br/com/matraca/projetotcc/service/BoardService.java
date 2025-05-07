package br.com.matraca.projetotcc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.matraca.projetotcc.model.entity.Board;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.model.entity.User;
import br.com.matraca.projetotcc.repository.BoardRepository;
import jakarta.transaction.Transactional;

@Service
public class BoardService {

    @Autowired
    BoardRepository repository;

    @Transactional // PRECISO APRENDER A USAR MELHOR, E ABSTRAIR MAIS ESSE MÉTODO SAVE
    public Board create(User user, Board newBoard) throws RuntimeException, IOException {
        List<Board> userBoards = user.getBoard();
        List<Button> userButtons = user.getButton();
        List<Button> buttonsNewBoard = newBoard.getButton();

        if (newBoard.getButton() == null || newBoard.getButton().isEmpty()) {
            throw new RuntimeException("Prancha não pode ser criada sem botões.");
        }

        if (userBoards.stream().anyMatch(board -> board.getName().equalsIgnoreCase(newBoard.getName()))) {
            throw new RuntimeException("Prancha já existe, com o nome: " + newBoard.getName());
        }

        for (int i = 0; i < buttonsNewBoard.size(); i++) {
            var id = buttonsNewBoard.get(i).getId();
            var matchingButton = userButtons.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Botão não encontrado: ID-" + id));
            
            buttonsNewBoard.set(i, matchingButton);
        }

        newBoard.setPosition(userBoards.size() + 1);
        newBoard.setUser(user);
        userBoards.add(newBoard);

        return repository.save(newBoard);
    }

    @Transactional
    public boolean delete(User user, Board board) throws RuntimeException {
        List<Board> userBoards = user.getBoard();
        Board boardToDelete = userBoards.stream()
                .filter(b -> b.getId().equals(board.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Prancha não encontrado: ID-" + board.getId()));

        return userBoards.remove(boardToDelete);
    }

    @Transactional
    public Board update(User user, Board updates) throws RuntimeException {
        List<Board> userBoards = user.getBoard();

        Board board = userBoards.stream()
        .filter(b -> b.getId().equals(updates.getId()))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Prancha não encontrada: ID-" + updates.getId()));


        if (updates.getName() != null) {
            board.setName(updates.getName());
        }
        if (updates.getButton() != null) {
            List<Button> userButtons = user.getButton();
            List<Button> buttonsNewBoard = updates.getButton();

            for (int i = 0; i < buttonsNewBoard.size(); i++) {
                var id = buttonsNewBoard.get(i).getId();
                var matchingButton = userButtons.stream()
                    .filter(b -> b.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Botão não encontrado: ID-" + id));
                
                buttonsNewBoard.set(i, matchingButton);
            }

            board.setButton(updates.getButton());
        }
    
        return repository.save(board);
    }
}
