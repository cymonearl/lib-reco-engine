# Welcome to lib-reco-engine

by Galzote, Gigawin, and Go!
open this by vscode or intelliJ

setting up javaFx in other system is a tedious task
we only needed to change the startup options or vmArgs

# vscode

...recommended...
no needed to setup the vmArgs

# intelliJ

...need to setup the vmArgs...
after cloning or downloading the project
open the "Main Menu" then "Run"
open "Open Configurations" then it opens a window
under the "Run/Debug Configurations" you should see the "Application" then open "App"


then modify the vmArgs text field
it should look like this:

--module-path "***" --add-modules javafx.controls,javafx.fxml


inside the "***" replace the stars to your "Working directory:" path then add the "\lib\javafx-sdk-23.0.1\lib"
it should at least look like this:

--module-path "C:\Users\creep\IdeaProjects\lib-reco-engine\lib\javafx-sdk-23.0.1\lib" --add-modules javafx.controls,javafx.fxml

after all that wearisome task, it should work