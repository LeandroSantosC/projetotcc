package br.com.matraca.projetotcc.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.matraca.projetotcc.dto.ApiResponse;
import br.com.matraca.projetotcc.model.entity.Auth;
import br.com.matraca.projetotcc.model.entity.Board;
import br.com.matraca.projetotcc.model.entity.User;
import br.com.matraca.projetotcc.service.UserService;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Board>>> getBoards(@AuthenticationPrincipal Auth auth) {
        User user = auth.getUser();
        List<Board> boards = user.getBoard();
        return ResponseEntity.ok(ApiResponse.success(boards));
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Board>> updateBoard(@AuthenticationPrincipal Auth auth, @PathVariable UUID id, @RequestBody Board updates) {
        User user = auth.getUser();
        updates.setId(id);
        Board board = userService.updateBoard(user, updates);

        return ResponseEntity.ok(ApiResponse.success(board));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ApiResponse<Board>> createBoard(@AuthenticationPrincipal Auth auth, @RequestBody Board newBoard) {
        User user = auth.getUser();

        Board board = userService.createBoard(user, newBoard);


        return ResponseEntity.ok(ApiResponse.success(board));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBoard(@AuthenticationPrincipal Auth auth, @PathVariable UUID id) {
        User user = auth.getUser();
        Board board = new Board(id);
        userService.deleteBoard(user, board);

        return ResponseEntity.ok(ApiResponse.success("Prancha "+ board.getName() + " deletado com sucesso!"));
    }
}
