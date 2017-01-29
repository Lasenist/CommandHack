package views.computer.interfaces;

import views.commandprompt.commands.interfaces.IFolderChangeListener;

/**
 * Created by Lasen on 22/01/17.
 */
public interface IConnection
{
    String getUsername();

    String getComputerHostname();

    String getCurrentPath();

    IFolderViewModel getCurrentFolder();

    void setCurrentFolder( IFolderViewModel currentFolder );

    IFolderViewModel getRootFolder();

    void addFolderChangeListener( IFolderChangeListener folderChangeListener );
}
