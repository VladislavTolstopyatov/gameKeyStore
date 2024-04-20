package servlets;

import dao.GameDao;
import dao.KeyDao;
import dto.GameDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.CreateGameMapper;
import mappers.GameMapper;
import mappers.UpdateGameMapper;
import services.GameService;
import util.JspHelper;

import java.io.IOException;

import static util.UrlPathUtil.FIND_GAME;

@WebServlet(FIND_GAME)
public class findGameByTitleServlet extends HttpServlet {
    private final GameService gameService = new GameService(new GameDao(),
            new KeyDao(),
            new GameMapper(),
            new CreateGameMapper(),
            new UpdateGameMapper());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.get("findGame")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String gameTitle = req.getParameter("gameName");
        GameDto gameDto = gameService.findByTitle(gameTitle);
        if (gameDto != null) {
            req.setAttribute("game", gameDto);
        } else {
            req.setAttribute("message","Игра не найдена");
        }
        req.getRequestDispatcher(JspHelper.get("findGame")).forward(req, resp);
    }
}
