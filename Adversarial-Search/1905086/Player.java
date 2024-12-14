public class Player {
    private String pType;
    private int[] state;
    private boolean pMode;
    private int noCaptured;
    private int additionalMoves;
    private int value;

    public Player(String pType) {
        this.pType = pType;
        this.state = new int[]{4,4,4,4,4,4,0};
        if(this.pType.equalsIgnoreCase("Computer")){
            this.pMode = false;
            this.value = Integer.MAX_VALUE;
        }
        else{
            this.pMode = true;
            this.value = Integer.MIN_VALUE;
        }
        this.additionalMoves = 0;
        this.noCaptured = 0;
    }
    public Player(Player player) {
        this.pType = player.getpType();
        if(this.pType.equalsIgnoreCase("Computer")){
            this.pMode = false;
            this.value = Integer.MAX_VALUE;
        }
        else{
            this.pMode = true;
            this.value = Integer.MIN_VALUE;
        }
        this.additionalMoves = player.getAdditionalMoves();
        this.noCaptured = player.getNoCaptured();
    }

    public void setState(int[] state) {
        this.state = state;
    }

    public boolean ispMode() {
        return pMode;
    }

    public int getNoCaptured() {
        return noCaptured;
    }

    public void setNoCaptured(int noCaptured) {
        this.noCaptured = noCaptured;
    }

    public int getAdditionalMoves() {
        return additionalMoves;
    }

    public void setAdditionalMoves(int additionalMoves) {

        this.additionalMoves = additionalMoves;
    }
    public int getNoStones(){
        int x = 0;      /*needs to set state first*/
        for(int i = 0; i < 6 ; i++ ){
            x += this.state[i];
        }
        return x;
    }
    public int getStorageStone(){
        return this.state[6];
    }

    public String getpType() {
        return pType;
    }
}
