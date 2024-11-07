package controller;
import BIN.AddPlayFrm;
import java.io.IOException;
import java.util.List;
import model.User;
import view.*;
import javax.swing.*;
import model.History;
import model.Pikachu;
public class Client {
    public static User user;
    public static AdminFrm adminFrm;
    public static AddPlayFrm addPlayFrm;
    public static LoginFrm loginFrm;
    public static RegisterFrm registerFrm;
    public static HomePageFrm homePageFrm;
    public static RoomListFrm roomListFrm;
    public static FriendListFrm friendListFrm;
    public static FindRoomFrm findRoomFrm;
    public static WaitingRoomFrm waitingRoomFrm;
    public static GameClientFrm gameClientFrm;
    public static CreateRoomPasswordFrm createRoomPasswordFrm;
    public static JoinRoomPasswordFrm joinRoomPasswordFrm;
    public static CompetitorInfoFrm competitorInfoFrm;
    public static RankFrm rankFrm;
    public static ResultNotificationFrm resultNotificationFrm;
    public static GameNoticeFrm gameNoticeFrm;
    public static FriendRequestFrm friendRequestFrm;
    public static RoomNameFrm roomNameFrm;
    public static HistoryFrm historyFrm; 
    public static SocketHandle socketHandle;

    
    public Client() {
    }

    public static JFrame getVisibleJFrame() {
        if (roomListFrm != null && roomListFrm.isVisible())
            return roomListFrm;
        if (friendListFrm != null && friendListFrm.isVisible()) {
            return friendListFrm;
        }
        if (createRoomPasswordFrm != null && createRoomPasswordFrm.isVisible()) {
            return createRoomPasswordFrm;
        }
        if (joinRoomPasswordFrm != null && joinRoomPasswordFrm.isVisible()) {
            return joinRoomPasswordFrm;
        }
        if (rankFrm != null && rankFrm.isVisible()) {
            return rankFrm;
        }
        return homePageFrm;
    }

    public static void openView(View viewName) throws IOException {
        if (viewName != null) {
            switch (viewName) {
                case ADDPLAY:
                    addPlayFrm = new AddPlayFrm();
                    addPlayFrm.setVisible(true);
                    break;
                case LOGIN:
                    loginFrm = new LoginFrm();
                    loginFrm.setVisible(true);
                    break;
                case REGISTER:
                    registerFrm = new RegisterFrm();
                    registerFrm.setVisible(true);
                    break;
                case HOMEPAGE:
                    homePageFrm = new HomePageFrm();
                    homePageFrm.setVisible(true);
                    break;
                case ROOM_LIST:
                    roomListFrm = new RoomListFrm();
                    roomListFrm.setVisible(true);
                    break;
                case FRIEND_LIST:
                    friendListFrm = new FriendListFrm();
                    friendListFrm.setVisible(true);
                    break;
                case FIND_ROOM:
                    findRoomFrm = new FindRoomFrm();
                    findRoomFrm.setVisible(true);
                    break;
                case WAITING_ROOM:
                    waitingRoomFrm = new WaitingRoomFrm();
                    waitingRoomFrm.setVisible(true);
                    break;
                case CREATE_ROOM_PASSWORD:
                    createRoomPasswordFrm = new CreateRoomPasswordFrm();
                    createRoomPasswordFrm.setVisible(true);
                    break;
                case RANK:
                    rankFrm = new RankFrm();
                    rankFrm.setVisible(true);
                    break;
                case ROOM_NAME_FRM:
                    roomNameFrm = new RoomNameFrm();
                    roomNameFrm.setVisible(true);           
            }
        }
    }

    public static void openView(View viewName, int arg1, String arg2) {
        if (viewName != null) {
            switch (viewName) {
                case JOIN_ROOM_PASSWORD:
                    joinRoomPasswordFrm = new JoinRoomPasswordFrm(arg1, arg2);
                    joinRoomPasswordFrm.setVisible(true);
                    break;
                case FRIEND_REQUEST:
                    friendRequestFrm = new FriendRequestFrm(arg1, arg2);
                    friendRequestFrm.setVisible(true);
            }
        }
    }
    
    public static void openView(View viewName, List<Pikachu> listPikachu, String str) {
        if (viewName != null) {
            switch (viewName) {
                case ADMIN:
                    adminFrm = new AdminFrm(listPikachu);
                    adminFrm.setVisible(true);
                    break;
            }
        }
    }
    
    

    public static void openView(View viewName, User competitor, int room_ID, String competitorIP, int [][]matrix, String pikachuStr) throws IOException {
        if (viewName == View.GAME_CLIENT) {
            gameClientFrm = new GameClientFrm(competitor, room_ID, competitorIP, matrix, pikachuStr);
            gameClientFrm.setVisible(true);
        }
    }

    public static void openView(View viewName, User user) {
        if (viewName == View.COMPETITOR_INFO) {
            competitorInfoFrm = new CompetitorInfoFrm(user);
            competitorInfoFrm.setVisible(true);
        }
    }

    public static void openView(View viewName, String arg1, String arg2) {
        if (viewName != null) {
            switch (viewName) {
                case GAME_NOTICE:
                    gameNoticeFrm = new GameNoticeFrm(arg2);
                    gameNoticeFrm.setVisible(true);
                    break;
                case LOGIN:
                    loginFrm = new LoginFrm(arg1, arg2);
                    loginFrm.setVisible(true);
            }
        }
    }
    
    public static void openView(View viewName, String result, String avatarCompetitor, String nameCompetitor, int myScore, int competitorScore) throws IOException{
        if(viewName== View.RESULT_NOTIFICATION_FRM){
            resultNotificationFrm = new ResultNotificationFrm(result, avatarCompetitor, nameCompetitor, myScore, competitorScore);
            resultNotificationFrm.setVisible(true);
        }
    }
    
    public static void openView(View viewName, List<History> listHistory){
        if(viewName==View.HISTORY_FRM){
            historyFrm = new HistoryFrm(listHistory);
            historyFrm.setVisible(true);
        }
    }

    public static void closeView(View viewName) {
        if (viewName != null) {
            switch (viewName) {
                case ADMIN:
                    adminFrm.dispose();
                    break;
                case ADDPLAY:
                    addPlayFrm.dispose();
                    break;
                case LOGIN:
                    loginFrm.dispose();
                    break;
                case REGISTER:
                    registerFrm.dispose();
                    break;
                case HOMEPAGE:
                    homePageFrm.dispose();
                    break;
                case ROOM_LIST:
                    roomListFrm.dispose();
                    break;
                case FRIEND_LIST:
                    friendListFrm.stopAllThread();
                    friendListFrm.dispose();
                    break;
                case FIND_ROOM:
                    findRoomFrm.stopAllThread();
                    findRoomFrm.dispose();
                    break;
                case WAITING_ROOM:
                    waitingRoomFrm.dispose();
                    break;
                case GAME_CLIENT:
                    gameClientFrm.stopAllThread();
                    gameClientFrm.dispose();
                    break;
                case CREATE_ROOM_PASSWORD:
                    createRoomPasswordFrm.dispose();
                    break;
                case JOIN_ROOM_PASSWORD:
                    joinRoomPasswordFrm.dispose();
                    break;
                case COMPETITOR_INFO:
                    competitorInfoFrm.dispose();
                    break;
                case RANK:
                    rankFrm.dispose();
                    break;
                case GAME_NOTICE:
                    gameNoticeFrm.dispose();
                    break;
                case FRIEND_REQUEST:
                    friendRequestFrm.dispose();
                    break;
                case ROOM_NAME_FRM:
                    roomNameFrm.dispose();
                    break;
                case RESULT_NOTIFICATION_FRM:
                    resultNotificationFrm.dispose();
                    break;
                case HISTORY_FRM:
                    historyFrm.dispose();
                    break;
            }

        }
    }

    public static void closeAllViews() {
        if(adminFrm != null) adminFrm.dispose();
        if(addPlayFrm != null) addPlayFrm.dispose();
        if (loginFrm != null) loginFrm.dispose();
        if (registerFrm != null) registerFrm.dispose();
        if (homePageFrm != null) homePageFrm.dispose();
        if (roomListFrm != null) roomListFrm.dispose();
        if (friendListFrm != null) {
            friendListFrm.stopAllThread();
            friendListFrm.dispose();
        }
        if (findRoomFrm != null) {
            findRoomFrm.stopAllThread();
            findRoomFrm.dispose();
        }
        if (waitingRoomFrm != null) waitingRoomFrm.dispose();
        if (gameClientFrm != null) {
            gameClientFrm.stopAllThread();
            gameClientFrm.dispose();
        }
        if (createRoomPasswordFrm != null) createRoomPasswordFrm.dispose();
        if (joinRoomPasswordFrm != null) joinRoomPasswordFrm.dispose();
        if (competitorInfoFrm != null) competitorInfoFrm.dispose();
        if (rankFrm != null) rankFrm.dispose();
        if (gameNoticeFrm != null) gameNoticeFrm.dispose();
        if (friendRequestFrm != null) friendRequestFrm.dispose();
        if (roomNameFrm != null) roomNameFrm.dispose();
        if (resultNotificationFrm != null) resultNotificationFrm.dispose();
        if (historyFrm!=null) historyFrm.dispose();
    }

    public static void main(String[] args) {
        new Client().initView();
    }

    public void initView() {
        loginFrm = new LoginFrm();
        loginFrm.setVisible(true);
        socketHandle = new SocketHandle();
        socketHandle.run();
    }

    public enum View {
        ADMIN,
        ADDPLAY,
        LOGIN,
        REGISTER,
        HOMEPAGE,
        ROOM_LIST,
        FRIEND_LIST,
        FIND_ROOM,
        WAITING_ROOM,
        GAME_CLIENT,
        CREATE_ROOM_PASSWORD,
        JOIN_ROOM_PASSWORD,
        COMPETITOR_INFO,
        RANK,
        GAME_NOTICE,
        FRIEND_REQUEST,
        ROOM_NAME_FRM,
        RESULT_NOTIFICATION_FRM,
        HISTORY_FRM
    }
}
