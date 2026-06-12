@echo off
REM Script pour initialiser la base de données MySQL

echo.
echo ====================================
echo Configuration MySQL
echo ====================================
echo.

REM Vérifier si MySQL est installé
mysql --version >nul 2>&1
if errorlevel 1 (
    echo [ERREUR] MySQL n'est pas trouvé dans le PATH
    echo Veuillez installer MySQL Community Server ou ajouter le chemin MySQL au PATH
    echo.
    echo Chemin courant du PATH:
    echo %PATH%
    echo.
    pause
    exit /b 1
)

echo [OK] MySQL trouvé

echo.
echo Exécution du script d'initialisation...
echo.

REM Exécuter le script SQL
mysql -u root < init-db.sql

if errorlevel 1 (
    echo.
    echo [ERREUR] Impossible de créer la base de données
    echo Assurez-vous que MySQL est en cours d'exécution et que root n'a pas de mot de passe
    echo.
    pause
    exit /b 1
)

echo.
echo ====================================
echo [SUCCESS] Base de données créée!
echo ====================================
echo.
echo Vous pouvez maintenant lancer: mvn spring-boot:run
echo.
pause
