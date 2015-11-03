## Как запустить проект ##

### Установка Android SDK ###
http://code.google.com/p/pointwars/wiki/AndroidSDKinstall
Нужно установить версию Androit 2.2 (API 8) - т. к. она выбрана минимальной.

### Debugging android client ###
  * [Device](http://developer.android.com/guide/developing/device.html)
  * [Android Virtual Device](http://developer.android.com/guide/developing/devices/index.html)

### Сборка и запуск android-клиента ###

  1. Открыть Eclipse
  1. File->Import->General->Existing Projects into Workspace, в качестве root directory указать каталог client\android
  1. Project->Properties->Java Build Path:
    * вкладка Source->Link Source... выбрать папку common-src
    * вкладка Libraries->Add External JARs... добавить из подпапок папки third\_party следующие файлы:
      * gson-2.1.jar
      * log4j-1.2.16.jar
  1. Попробовать запустить проект через эмулятор или с помощью девайса

  * TODO:
    * Переместить andEngine в third\_party?
    * Возможно, при использовании выше Add External JARs... клиент не будет собираться. Тогда нужно поместить необходимые библиотеки в папку client\android\libs и подключить их оттуда с помощью Add JARs...

### Сборка и запуск сервера ###
  1. Открыть Eclipse и сделать новый Java Project
  1. Project->Properties->Java Build Path:
    * вкладка Source->Link Source... выбрать папку server-src
    * вкладка Source->Link Source... выбрать папку common-src
    * вкладка Libraries->Add External JARs... добавить из подпапок папки third\_party следующие файлы:
      * gson-2.1.jar
      * log4j-1.2.16.jar


### Тестирование клиентов и сервера с помощью `ServerClientTestsLauncher` ###
> Для тестирования клиентов сделан специальный класс `ServerClientTestsLauncher`. У класса есть поле mode. У него есть два занчения:
  * FakeVsFake: действия всех игроков эмулируются. Тест быстро выполняется и завершается.
  * AndriodVsFake: эмулируются действия только одного игрока. Тест не завершается, чтобы было удобно отлаживать реальное взаимодействие.
> Для тестирования andriod клиента сделан специальный тест AndriodVsFakePlayerTest. Он как и все остальные запускаются из `ServerClientTestsLauncher`. Для тестирования:
    1. Узнать "реальный" IP адрес компьютера, на котором запускается тест. (например у меня все происходит в локальной сети wifi и я брал адрес компьютера в этой сети). Этот IP вписать в поле host в классе `WordsBattleActivity`.
    1. Присвоить полю `mode` класса `ServerClientTestsLauncher` значение `AndriodVsFake`.
    1. Запустить `ServerClientTestsLauncher` как `junit test`.
    1. Запустить приложение на andriod.
    1. (релевантно для [r81](https://code.google.com/p/words-battle/source/detail?r=81)) можно нажимать на букву в приложении, либо брать от лица оппонента из консоли. (буква береться по ID. Для выхода 'q').
    1. Для завершения теста нажмите enter. (либо остановите процесс)
_Примечание: Если клиент отключился, оппонент пока не оповещается об этом и остается в игре. Пока, чтобы заного полключиться клиентом, нужно перезапустить тест_