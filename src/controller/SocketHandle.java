package controller;

import model.User;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import BIN.Play;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.History;
import model.Pikachu;

public class SocketHandle implements Runnable {
    private BufferedWriter outputWriter;
    private Socket socketOfClient;

    public List<User> getListUser(String[] message) {
        List<User> friend = new ArrayList<>();
        for (int i = 1; i < message.length; i = i + 4) {
            friend.add(new User(Integer.parseInt(message[i]),
                    message[i + 1],
                    message[i + 2].equals("1"),
                    message[i + 3].equals("1")));
        }
        return friend;
    }
    
    public List<Play> getListPlay(String[] message){
        List<Play> listPlay = new ArrayList<>();
        for(int i = 1; i< message.length;i+=2){
            listPlay.add(new Play(Integer.parseInt(message[i]),message[i+1]));
        }
        return listPlay;
    }
    
    public List<User> getListRank(String[] message) {
        List<User> friend = new ArrayList<>();
        for (int i = 1; i < message.length; i = i + 9) {
            friend.add(new User(Integer.parseInt(message[i]),
                    message[i + 1],
                    message[i + 2],
                    message[i + 3],
                    message[i + 4],
                    Integer.parseInt(message[i + 5]),
                    Integer.parseInt(message[i + 6]),
                    Integer.parseInt(message[i + 7]),
                    Integer.parseInt(message[i + 8])));
        }
        return friend;
    }

    public User getUserFromString(int start, String[] message) {
        return new User(Integer.parseInt(message[start]),
                message[start + 1],
                message[start + 2],
                message[start + 3],
                message[start + 4],
                Integer.parseInt(message[start + 5]),
                Integer.parseInt(message[start + 6]),
                Integer.parseInt(message[start + 7]),
                Integer.parseInt(message[start + 8]));
    }
    
    public int[][] getMatrix(int start, String[] message){
        int[][] matrix = new int[7][7];
        int cnt = 0;
        for(int i = 0; i < 7;i++){
            for(int j = 0; j < 7; j++){
                matrix[i][j] = Integer.parseInt(message[start+cnt]);
                cnt++;
            }
        }
        return matrix;
    }
    
    public String getPikachuStr(int start, String[] message){
        String result = message[start]+"";
        for(int i = start+1; i < message.length;i++){
            result = result + ","+message[i];
        }
        return result;
    }

    @Override
    public void run() {

        try {
            socketOfClient = new Socket("127.0.0.1", 7777);
            System.out.println("Kết nối thành công!");
            outputWriter = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
            String message;
            while (true) {
                message = inputReader.readLine();
                if (message == null) {
                    break;
                }
                
                
                String[] messageSplit = message.split(",");
                
                if (messageSplit[0].equals("update-user-info")) {
                    Client.closeAllViews();
                    Client.user = getUserFromString(1, messageSplit);
                    Client.openView(Client.View.HOMEPAGE);
                }
                
                
                if(messageSplit[0].equals("admin-login-success")){
                    List<Pikachu> listPikachu = new ArrayList<>();
                    for(int i = 1; i < messageSplit.length;i+=2){
                        listPikachu.add(new Pikachu(Integer.parseInt(messageSplit[i]),messageSplit[i+1]));
                    }
                    Client.closeAllViews();
                    Client.openView(Client.View.ADMIN, listPikachu, "str");
                }
                if (messageSplit[0].equals("server-send-id")) {
                    int serverId = Integer.parseInt(messageSplit[1]);
                }
                //Đăng nhập thành công
                if (messageSplit[0].equals("login-success")) {
                    System.out.println("Đăng nhập thành công");
                    Client.closeAllViews();
                    Client.user = getUserFromString(1, messageSplit);
                    Client.openView(Client.View.HOMEPAGE);
                }
                //Thông tin tài khoản sai
                if (messageSplit[0].equals("wrong-user")) {
                    System.out.println("Thông tin sai");
                    Client.closeView(Client.View.GAME_NOTICE);
                    Client.openView(Client.View.LOGIN, messageSplit[1], messageSplit[2]);
                    Client.loginFrm.showError("Tài khoản hoặc mật khẩu không chính xác");
                }
                //Tài khoản đã đăng nhập ở nơi khác
                if (messageSplit[0].equals("dupplicate-login")) {
                    System.out.println("Đã đăng nhập");
                    Client.closeView(Client.View.GAME_NOTICE);
                    Client.openView(Client.View.LOGIN, messageSplit[1], messageSplit[2]);
                    Client.loginFrm.showError("Tài khoản đã đăng nhập ở nơi khác");
                }
                //Tài khoản đã bị banned
                if (messageSplit[0].equals("banned-user")) {
                    Client.closeView(Client.View.GAME_NOTICE);
                    Client.openView(Client.View.LOGIN, messageSplit[1], messageSplit[2]);
                    Client.loginFrm.showError("Tài khoản đã bị ban");
                }
                //Xử lý register trùng tên
                if (messageSplit[0].equals("duplicate-username")) {
                    Client.closeAllViews();
                    Client.openView(Client.View.REGISTER);
                    JOptionPane.showMessageDialog(Client.registerFrm, "Tên tài khoản đã được người khác sử dụng");
                }
                //Xử lý nhận thông tin, chat từ toàn server
                if (messageSplit[0].equals("chat-server")) {
                    if (Client.homePageFrm != null) {
                        Client.homePageFrm.addMessage(messageSplit[1]);
                    }
                }
                //Xử lý nhận thông tin, chat trong trận
                if (messageSplit[0].equals("chat")) {
                    Client.gameClientFrm.addMessage(messageSplit[1]);
                }
                //Xử lý hiển thị thông tin đối thủ là bạn bè/không
                if (messageSplit[0].equals("check-friend-response")) {
                    if (Client.competitorInfoFrm != null) {
                        Client.competitorInfoFrm.checkFriend((messageSplit[1].equals("1")));
                    }
                }
                //Xử lý khi admin trả về danh sách pikachu
                if(messageSplit[0].equals("return-list-pikachu")){
                    List<Pikachu> listPikachu = new ArrayList<>();
                    for(int i = 1; i < messageSplit.length;i+=2){
                        listPikachu.add(new Pikachu(Integer.parseInt(messageSplit[i]),messageSplit[i+1]));
                    }
                    Client.adminFrm.updateListPikachu(listPikachu);
                }
                //Xử lý khi client nhận list lịch sử
                if (messageSplit[0].equals("returnHistory")) {
                    List<History> listHistory = new ArrayList<>();
                    for(int i = 1; i < messageSplit.length;i+=4){
                        History data = new History(messageSplit[i],Integer.parseInt(messageSplit[i+1]),Integer.parseInt(messageSplit[i+2]),messageSplit[i+3]);
                        if(data.getScoreUser1()<data.getScoreUser2()) data.setResult("Thua");
                        else if(data.getScoreUser1()==data.getScoreUser2()) data.setResult("Hòa");
                        else data.setResult("Thắng");
                        listHistory.add(data);
                    }
                    Client.openView(Client.View.HISTORY_FRM, listHistory);
                }
                
                //Xử lý khi client chơi xong trận và server gửi điểm
                if (messageSplit[0].equals("result")) {
                    String result = messageSplit[1];
                    String avatarCompetitor = messageSplit[2];
                    String nameCompetitor = messageSplit[3];
                    int myScore = Integer.parseInt(messageSplit[4]);
                    int competitorScore = Integer.parseInt(messageSplit[5]);
                    Client.openView(Client.View.RESULT_NOTIFICATION_FRM,result, avatarCompetitor, nameCompetitor,myScore,competitorScore);
                }
                
                //Xử lý kết quả tìm phòng từ server
                if (messageSplit[0].equals("room-fully")) {
                    Client.closeAllViews();
                    Client.openView(Client.View.HOMEPAGE);
                    JOptionPane.showMessageDialog(Client.homePageFrm, "Phòng chơi đã đủ 2 người chơi");
                }
                // Xử lý không tìm thấy phòng trong chức năng vào phòng
                if (messageSplit[0].equals("room-not-found")) {
                    Client.closeAllViews();
                    Client.openView(Client.View.HOMEPAGE);
                    JOptionPane.showMessageDialog(Client.homePageFrm, "Không tìm thấy phòng");
                }
                // Xử lý phòng có mật khẩu sai
                if (messageSplit[0].equals("room-wrong-password")) {
                    Client.closeAllViews();
                    Client.openView(Client.View.HOMEPAGE);
                    JOptionPane.showMessageDialog(Client.homePageFrm, "Mật khẩu phòng sai");
                }
                //Xử lý xem rank
                if (messageSplit[0].equals("return-get-rank-charts")) {
                    if (Client.rankFrm != null) {
                        Client.rankFrm.setDataToTable(getListRank(messageSplit));
                    }
                }
                
//                if(messageSplit[0].equals("return-play-list")){
//                    if(Client.adminFrm!=null){
//                        Client.adminFrm.setDataToTable(getListPlay(messageSplit));
//                    }
//                }
                //Xử lý lấy danh sách phòng
                if (messageSplit[0].equals("room-list")) {
                    Vector<String> rooms = new Vector<>();
                    Vector<String> passwords = new Vector<>();
                    for (int i = 1; i < messageSplit.length; i = i + 2) {
                        rooms.add("Phòng " + messageSplit[i]);
                        passwords.add(messageSplit[i + 1]);
                    }
                    Client.roomListFrm.updateRoomList(rooms, passwords);
                }
                if (messageSplit[0].equals("return-friend-list")) {
                    if (Client.friendListFrm != null) {
                        Client.friendListFrm.updateFriendList(getListUser(messageSplit));
                    }
                }
                if (messageSplit[0].equals("go-to-room")) {
                    int roomID = Integer.parseInt(messageSplit[1]);
                    String competitorIP = messageSplit[2];
                    User competitor = getUserFromString(3, messageSplit);
                    int[][]matrix = getMatrix(12,messageSplit);
                    String pikachuStr = getPikachuStr(61, messageSplit);
                    if (Client.findRoomFrm != null) {
                        Client.findRoomFrm.showFoundRoom();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            JOptionPane.showMessageDialog(Client.findRoomFrm, "Lỗi khi sleep thread");
                        }
                    } else if (Client.waitingRoomFrm != null) {
                        Client.waitingRoomFrm.showFoundCompetitor();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            JOptionPane.showMessageDialog(Client.waitingRoomFrm, "Lỗi khi sleep thread");
                        }
                    }
                    Client.closeAllViews();
                    System.out.println("Đã vào phòng: " + roomID);
                    //Xử lý vào phòng
                    Client.openView(Client.View.GAME_CLIENT
                            ,competitor
                            ,roomID
                            ,competitorIP
                            ,matrix
                            ,pikachuStr);
                }
                //Tạo phòng và server trả về tên phòng
                if (messageSplit[0].equals("your-created-room")) {
                    Client.closeAllViews();
                    Client.openView(Client.View.WAITING_ROOM);
                    Client.waitingRoomFrm.setRoomName(messageSplit[1]);
                    if (messageSplit.length == 3)
                        Client.waitingRoomFrm.setRoomPassword(messageSplit[2]);
                }
                //Xử lý yêu cầu kết bạn tới
                if (messageSplit[0].equals("make-friend-request")) {
                    int ID = Integer.parseInt(messageSplit[1]);
                    String nickname = messageSplit[2];
                    Client.openView(Client.View.FRIEND_REQUEST, ID, nickname);
                }
                //Xử lý khi nhận được yêu cầu thách đấu
                if (messageSplit[0].equals("duel-notice")) {
                    int res = JOptionPane.showConfirmDialog(Client.getVisibleJFrame(), "Bạn nhận được lời thách đấu của " + messageSplit[2] + " (ID=" + messageSplit[1] + ")", "Xác nhận thách đấu", JOptionPane.YES_NO_OPTION);
                    if (res == JOptionPane.YES_OPTION) {
                        Client.socketHandle.write("agree-duel," + messageSplit[1]);
                    } else {
                        Client.socketHandle.write("disagree-duel," + messageSplit[1]);
                    }
                }
                //Xử lý không đồng ý thách đấu
                if (messageSplit[0].equals("disagree-duel")) {
                    Client.closeAllViews();
                    Client.openView(Client.View.HOMEPAGE);
                    JOptionPane.showMessageDialog(Client.homePageFrm, "Đối thủ không đồng ý thách đấu");
                }
                //Xử lý đánh một nước trong ván chơi
                
                    
                if (messageSplit[0].equals("left-room")) {
                    Client.gameClientFrm.stopTimer();
                    Client.closeAllViews();
                    Client.openView(Client.View.GAME_NOTICE, "Đối thủ đã thoát khỏi phòng", "Đang trở về trang chủ");
                    Thread.sleep(3000);
                    Client.closeAllViews();
                    Client.openView(Client.View.HOMEPAGE);
                }
                //Xử lý bị banned
                if (messageSplit[0].equals("banned-notice")) {
                    Client.socketHandle.write("offline," + Client.user.getID());
                    Client.closeAllViews();
                    Client.openView(Client.View.LOGIN);
                    JOptionPane.showMessageDialog(Client.loginFrm, messageSplit[1], "Bạn đã bị BAN", JOptionPane.WARNING_MESSAGE);
                }
                //Xử lý cảnh cáo
                if (messageSplit[0].equals("warning-notice")) {
                    JOptionPane.showMessageDialog(null, messageSplit[1], "Bạn nhận được một cảnh cáo", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String message) throws IOException {
        outputWriter.write(message);
        outputWriter.newLine();
        outputWriter.flush();
    }

    public Socket getSocketOfClient() {
        return socketOfClient;
    }

}
