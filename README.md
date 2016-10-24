# package-material-skeleton-plugin

Skeleton for a package material plugin with gradle. Since every package management tool works a little bit differently, it won't work out of the box.

Development Process:
* Documentation on package material plugins can be found [here](https://developer.go.cd/current/writing_go_plugins/package_material/json_message_based_package_material_extension.html)
* Everywhere you need to update will contain the word "skeleton," either in the code or in a comment in the relevant section -- we've just put in placeholder notes for guidance, but the implementation's up to you!
  * We also use "skeleton" to indicate custom fields (e.g. ```SKELETON_USERNAME``` in case you want to support a private repository)

Build and run it:

1. Stop server
2. ```gradle build && cp \path\to\project\build\libs\go-task-skeleton-1.0.jar \path\to\go\server\plugins\external```
3. Start server