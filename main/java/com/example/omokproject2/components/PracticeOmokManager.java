package com.example.omokproject2.components;

import android.graphics.Color;
import android.util.Log;

import com.example.omokproject2.api.ApiManager;
import com.example.omokproject2.api.GameDataForm;
import com.example.omokproject2.api.ResponseForm;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PracticeOmokManager extends OmokEngine {

    private BoardView boardView;
    private ApiManager apiManager;
    private List<Integer> tmpLog;

    public PracticeOmokManager(BoardView boardView){
        this.boardView = boardView;
        apiManager = new ApiManager();
    }

    public int getNumber(){
        return this.gameLog.size();
    }

    public boolean put(int x, int y){
        if(super.put(x, y)){
            checkGameState(x, y);
            refresh();
            tmpLog = null;
            return true;
        }
        return false;
    }

    private void checkGameState(int coor){ checkGameState(getX(coor), getY(coor)); }
    private void checkGameState(int x, int y){
        if(gameState == Const.BLACK_WIN) {
            addEndSpots(x, y, isEnd(x, y, Const.BLACK));
            sendGameData();
        }
        else if(gameState == Const.WHITE_WIN) {
            addEndSpots(x, y, isEnd(x, y, Const.WHITE));
            sendGameData();
        }
        else if(gameState == Const.DRAW){
            sendGameData();
        }
    }

    private void addEndSpots(int X, int Y, int dir){
        int x = X, y = Y, color = getColor(X, Y);
        float size = boardView.getStoneSize()/3;
        while(true){
            x += Const.DIRX[dir]; y += Const.DIRY[dir];
            if(indexOut(x, y)) break;
            if(color != getColor(x, y)) break;
            boardView.addSpot(new Spot(x, y, size, Color.GREEN));
        }
        while(true){
            x -= Const.DIRX[dir]; y -= Const.DIRY[dir];
            if(indexOut(x, y)) break;
            if(color != getColor(x, y)) break;
            boardView.addSpot(new Spot(x, y, size, Color.GREEN));
        }
    }

    public void moveGameLog(int n){
        int end = 1;
        if(n > 0){
            if(tmpLog == null) return;
            end = gameLog.size() + n;
            if(end > tmpLog.size()) end = tmpLog.size();
        }
        else if(n < 0){
            if(tmpLog == null || tmpLog.size() < gameLog.size())
                tmpLog = new ArrayList<>(gameLog);
            end = gameLog.size() + n;
            if(end < 1) end = 1;
        }
        List<Integer> reset = new ArrayList<>();
        for(int i=0; i<end; i++) reset.add(tmpLog.get(i));
        resetBy(reset);
        boardView.clearSpots();
        checkGameState(tmpLog.get(end-1));
        refresh();
    }

    private void refresh(){
        if(boardView != null){
            if(gameState == Const.BLACK)
                boardView.refresh(board, true);
            else if(gameState == Const.WHITE)
                boardView.refresh(board, false);
            else if(gameState == Const.BLACK_WIN || gameState == Const.WHITE_WIN)
                boardView.refresh(board, false);
            else if(gameState == Const.DRAW)
                boardView.refresh(board, false);
            else{
                Log.e("refresh", "gameState="+gameState);
                boardView.refresh(board, true);
            }
        }
    }

    private void sendGameData(){
        GameDataForm form = new GameDataForm(0, 0, gameLog.toString(), gameState);
        Call<ResponseForm> call = apiManager.getApiService().saveGameData(form);
        call.enqueue(new Callback<ResponseForm>() {
            @Override
            public void onResponse(Call<ResponseForm> call, Response<ResponseForm> response){
                if(response.isSuccessful()){

                }
                else{
                    Log.e("saveGameData", "Request Info: " + call.request().toString());
                    Log.e("saveGameData", "fail code: "+response.code());
                }
            }
            @Override
            public void onFailure(Call<ResponseForm> call, Throwable t){
                Log.e("saveGameData", "Request Info: " + call.request().toString());
                Log.e("saveGameData", "Network Error: " + t.getMessage());
            }
        });
    }
}
