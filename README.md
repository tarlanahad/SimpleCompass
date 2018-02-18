# SimpleCompass
A beautifully designed simple Android Compass Implementation

# Screenshots

![screenshot_1518947634](https://user-images.githubusercontent.com/33389512/36350616-e5c5d670-14b4-11e8-8d1a-69379b19aeaa.png)

![screenshot_1518947719](https://user-images.githubusercontent.com/33389512/36350619-effca0d8-14b4-11e8-92b9-1f77a69c7c26.png)
_________________
# Import

*Add it in your _root_  *build.gradle* at the end of repositories:*

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
```
_____________________


*And on _module_  *build.gradle*:*

```
dependencies {
	        compile 'com.github.tarlanahad:SimpleCompass:1.0'
}
```
____________________


*In your AndroidManifest.xml file add:*

```
<activity
            android:name="com.tarlanahad.simplecompass.Activities.CompassActivity"
            android:screenOrientation="portrait"
            android:theme="@style/Theme.AppCompat.Light.NoActionBar"/>
```
__________________


*Finally, to start the activity, build *Simple Compass* like this:*

```  
new SimpleCompass().Builder(this)
                .withDestination(fromLocation, toLocation)
                .setContainerBackgroundColor(Color.WHITE)
                .setInfoText("Bearing from location A is ")
                .setCircleColor(Color.BLACK)
                .setCurrentDegreeColor(Color.BLACK)
                .setInfoTextColor(Color.BLACK)
                .setFinishActivityButtonColor(Color.BLACK)
                .setArrowColor(Color.DKGRAY)
                .build();
```
_________________

# Important

*To show the Views related to the destination such as *bearing* and *arrow* , the codes below have to be written.*
```
                .withDestination(fromLocation, toLocation)
                .setInfoText("Bearing from location A is ")
```



