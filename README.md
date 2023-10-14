# package-material-skeleton-plugin

Skeleton for a package material plugin with gradle. Since every package management tool works a little bit differently, it won't work out of the box.

Development Process:
* Documentation on package material plugins can be found [here](https://docs.gocd.org/current/extension_points/package_repository_extension.html) and [here](https://plugin-api.gocd.org/current/package-repo/)
* Everywhere you need to update will contain the word "skeleton," either in the code or in a comment in the relevant section -- we've just put in placeholder notes for guidance, but the implementation's up to you!
  * We also use "skeleton" to indicate custom fields (e.g. ```SKELETON_USERNAME``` in case you want to support a private repository)

Build and run it:

1. Stop server
2. ```gradle build && cp \path\to\project\build\libs\go-task-skeleton-1.0.jar \path\to\go\server\plugins\external```
3. Start server

## License

```plain
Copyright 2023 Thoughtworks, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## About the license and releasing your plugin under a different license

The skeleton code in this repository is licensed under the Apache 2.0 license. The license itself specifies the terms
under which derivative works may be distributed (the license also defines derivative works). The Apache 2.0 license is a
permissive open source license that has minimal requirements for downstream licensors/licensees to comply with.

This does not prevent your plugin from being licensed under a different license as long as you comply with the relevant
clauses of the Apache 2.0 license (especially section 4). Typically, you clone this repository and keep the existing
copyright notices. You are free to add your own license and copyright notice to any modifications.

This is not legal advice. Please contact your lawyers if needed.
