# AppChat
Práctica grupo 1.2 de la asignatura de Técnicas de Desarrollo de Software 2024/2025.\
* Emilio González Fernández Piqueras
* Frank Antonio Oldfield Montilla

## To-do list
- [ ] Instalar librería chat-window -> https://github.com/jesusc/asignatura-tds
- [ ] Plantear y crear ramas e issues necesarias
- [ ] Importar clases preexistentes
- [ ] Plantear y crear clases necesarias
- [ ] Seguir el proceso de desarrollo dado en clase



## Metodología set-up
### 1. Repositorio local
1. Instalación eclipse
2. Creación proyecto maven (Arquetipo quickstart)
3. Añadir dependencias en el fichero AppChat/pom.xml (jCalendar, ...)
4. Crear AppChat/.gitignore -> gitignore.io
5. Configuración repositorio local
   ```
   git init
   git add .
   git commit -m "First commit"
   ```
### 2. Repositorio remoto
  1. Creación repositorio vacío en github.com
  2. Invitar compañero de la práctica
### 3. Vincular repositorio local y remoto
   ```
   git remote add origin https://github.com/frankoldfield/AppChat.git
   git branch -M main
   git push -u origin main
   ```
### 4. Crear branch vacía para la revisión de la práctica
  ```
  git checkout -b vacia-para-revision
  ```
  Y para volver a la rama principal
  ``` git switch main ```
