# CFPRandomizer 0.21

This program is made for [Curve Fever Pro](https://curvefever.pro) players who want to test their skills and see how they do on randomly selected power combos.

To use the Randomizer, you need to press the selected keybind when you're in the game lobby, and it will execute a mouse macro, selecting randomly generated powers.

Randomizer assumes you have all powers unlocked, so if you're under level 58, it will select nothing instead of higher level powers that you're missing.

## *How do I use it?*

1. You need to have Java installed, you can download it [here](https://www.java.com/en/download/).
2. Click on [CFPRandomizer0.21.zip](CFPRandomizer0.21.zip) and press the ***Download*** button.
3. Extract the archive.
4. 
   - Windows: start `run_cfprandomizer.bat`
   - Linux/MacOS: in a command line run `java -jar CFPRandomizer0.21.jar`
5. Press a key with which you want to run the Randomizer
6. Use that key in the lobby and enjoy the game!

## *How random is it?*

The goal of CFPRandomizer is to allow the player to test how well-rounded their skillset is. In other words, not every power has an equal chance of being selected. Ideally, powers would have a weight according to how much gameplay variety it brings.

For instance, there are 8 fevers (green powers) that you can all use in pretty much the same way, their weights are lower. On the other hand, each passive (blue powers) requires different skills to use efficiently, therefore their weights are higher.

Since this gameplay variety is a metric that is very hard to measure on my own, I tried to roughly balance out some obvious cases. If we assume that each power has an equal chance of being selected, let's consider that chance as 1x. Then these powers have a modified chance:
 - Every shot (One Shot, Side Shot, Double Shot, Multi Shot) have a chance of 0.5x, since there are 4 of them;
 - Mine and Stealth Mine are basically the same concept with a slight difference between them, they also have a chance of 0.5x;
 - All 8 fevers have a total chance of being selected as 4x, with Reverse and Speedy having 0.75x chance, and everything else 0.417x.

Lastly, keep in mind that this is by no means a perfect probability distribution, the goal of making this program public in its current state is to hear the feedback of players who have tried using it and pinpoint the changes that need to be made.