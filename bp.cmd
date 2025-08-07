@echo off

echo Building JavaFX bundle...
ant -f "C:\Users\tanatips\Documents\nectec\autosync" -Djar.archive.disabled=true -Dnative.bundling.type=image build-native && (
    echo JavaFX build SUCCESS!
    echo.
    echo Creating installer...
    cd /d "C:\Program Files (x86)\Inno Setup 6" && iscc.exe "C:\Users\tanatips\Documents\nectec\autosync\ffc 2022.iss" && (
        echo.
        echo SUCCESS: Installer created!
        echo Opening output folder...
        start "" explorer "C:\Users\tanatips\Documents\nectec\autosync\output"
    ) || (
        echo ERROR: Inno Setup failed!
    )
) || (
    echo ERROR: JavaFX build failed!
)

echo.
pause