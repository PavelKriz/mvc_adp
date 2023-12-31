package cz.cvut.fit.miadp.mvcgame.controller;

import cz.cvut.fit.miadp.mvcgame.command.*;
import cz.cvut.fit.miadp.mvcgame.model.IGameModel;

import java.util.List;

public class GameController {

    public IGameModel model;

    public GameController(IGameModel model) {
        this.model = model;
    }

    public void processPressedKeys(List<String> pressedKeysCodes) {
        for (String code : pressedKeysCodes) {
            switch (code) {
                case "UP":
                    this.model.registerCommand(new MoveCannonUpCmd(this.model));
                    break;
                case "DOWN":
                    this.model.registerCommand(new MoveCannonDownCmd(this.model));
                    break;
                case "SPACE":
                    this.model.registerCommand(new CannonShootCmd(this.model));
                    break;
                case "A":
                    this.model.registerCommand(new AimCannonUpCmd(this.model));
                    break;
                case "Y":
                    this.model.registerCommand(new AimCannonDownCmd(this.model));
                    break;
                case "F":
                    this.model.registerCommand(new CannonPowerUpCmd(this.model));
                    break;
                case "D":
                    this.model.registerCommand(new CannonPowerDownCmd(this.model));
                    break;
                case "M":
                    this.model.registerCommand(new ToggleMovingStrategyCmd(this.model));
                    break;
                case "N":
                    this.model.registerCommand(new ToggleShootingModeCmd(this.model));
                    break;
                case "Z":
                    this.model.undoLastCommand();
                    break;
                case "H":
                    System.out.println("pressed H");
                    this.model.registerCommand(new ToggleEnemyMovingStrategyCmd(this.model));
                    break;

                default:
                    //nothing
            }
        }
    }
}
