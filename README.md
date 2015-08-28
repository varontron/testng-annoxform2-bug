# testng-annoxform2-bug
Example code for reproducing IAnnotationTransformer2 bug, [Issue #787](https://github.com/cbeust/testng/issues/787)

To reproduce the bug, just run `mvn test`

The bug manifests as follows:

When a custom annotation is applied to `@Test` methods, and the default annotation values are transformed by an implementation of `IAnnotationTransfermer2`, the first method appearing in the test class will lose the transformed annotation and revert to the default annotation values. Test configuration ordering does not appear to have an impact.  Only the actual order of methods in the class.

In `BasicTest` if you run as is you will see the following test output containing the default annotations for `aTest`:

```
aTest: default_aTestS1
aTest: default_aTestS2
bTest: xform_bTestS1
bTest: xform_bTestS2
bTest: xform_bTestS3
bTest: xform_bTestS4
```

If you un-comment the first method `preTest` you will instead see the transformed annotations for `aTest`
```
aTest: xform_aTestS1
aTest: xform_aTestS2
aTest: xform_aTestS3
aTest: xform_aTestS4
bTest: xform_bTestS1
bTest: xform_bTestS2
bTest: xform_bTestS3
bTest: xform_bTestS4
```

The first test in the class always reverts to the default annotation.
