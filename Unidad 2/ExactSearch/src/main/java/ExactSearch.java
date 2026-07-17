public class ExactSearch {
    private char[] query, target;


    public int indexOf(char[] query, char[] target){
        this.query = query;
        this.target = target;

        for(int i = 0; i < target.length; i++){
            if(query[0] == target[i]){
                matchs(query, target, i)
            }
        }
    }

    public int matchs(char[] query, char[] target, int index){
        for(int i = 0; i < Math.min(query.length , target.length - index) ; i++){
            if(query[i] != target[index]){
                return 0;
            }
        }
        return 1;
    }





}
