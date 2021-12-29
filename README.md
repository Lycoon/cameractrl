![Mod logo](/src/main/resources/logo.png)

<p align="center">
  <b>CameraCTRL</b> is a Minecraft mod that tweaks the game to make life of video makers easier
</p>

## How to use it?
The mod features 2 major tweaks, the first one allows you to save a camera profile which can be enabled and disabled with a keybind:
* Setup the FOV and mouse sensitivity you want for your camera profile in the Minecraft settings
* Press Y to copy them (you can now go back to your usual settings you use to play)
* Press R at anytime to apply the saved settings you copied (GUI is hidden, spectator and cinematic mode enabled)
* If you press R once more, you recover the settings you had before pressing it.

The second tweak is the addition of the `/loc` command:
* `/loc help` prints each usage for the loc command
* `/loc add <name>` adds your current location to the list
* `/loc remove <name>` removes the specified location from the list
* `/loc go <name>` teleports you to the specified location
* `/loc list` prints all the saved locations

## Is this mod useful?
Clearly not for everybody. I may plan on extending it to save even more settings in the future, however for the usage it is meant to be used, this would be useless. I got bored of always changing plenty of settings to just switch to a camera shoulder-like shot when I am recording machinimas, so I binded it all to one key.

## Do I need it on my server?
Yes. This mod **does need** to be installed on the server if you use one.

## Config
The config files are generated in the `.minecraft/config/CameraCTRL/` directory.
* `camera.cfg` contains the saved camera profile mouse sensitivity and FOV values
* `old.cfg` contains the mouse sensitivity and FOV values you had before activating your camera profile
* `locations.cfg` contains the names and coordinates of your saved locations
