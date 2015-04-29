import com.beust.jcommander.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liam on 4/7/15.
 */
public class JCommanderHelper {
    @Parameter(names = "-n", description = "Output of NFA")
    private String nfaOutputFile = null;

    @Parameter(names = "-d", description = "Output of DFA")
    private String dfaOutputFile = null;

    @Parameter(description = "Files")
    private ArrayList<String> namelessParams = new ArrayList<String>();

    public List<String> getNamelessParams(){
        return namelessParams;
    }
}
