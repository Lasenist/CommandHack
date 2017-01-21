package views.computer.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lasen on 19/12/16.
 */
public interface IFolderViewModel
{
    String getName();
    HashMap<String, IFolderViewModel> getContainingFolders();
    ArrayList<String> getPath();
}
