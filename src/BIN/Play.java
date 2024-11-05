
package BIN;

public class Play {
    private int id;
    private String input0;
    private int[] input;
    
    public Play(int id, String input0){
        this.id = id;
        this.input0 = input0;
    }
    
    public Play(int id, String input0, int[] input) {
        this.id = id;
        this.input0 = input0;
        this.input = input;
    }
    
    public Play(String input0, int[] input) {
        this.input0 = input0;
        this.input = input;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInput0() {
        return input0;
    }

    public void setInput0(String input0) {
        this.input0 = input0;
    }

    public int[] getInput() {
        return input;
    }

    public void setInput(int[] input) {
        this.input = input;
    }
}
