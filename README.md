# [JsfWarn](http://www.knitelius.com/jsfwarn/)

JsfWarn enables non-terminal field-level JSF validations.

* Homepage: [http://www.knitelius.com/jsfwarn](http://www.knitelius.com/jsfwarn)
* Twitter: [@sknitelius](https://twitter.com/sknitelius)
* Source: [https://github.com/sknitelius/jsfwarn](https://github.com/sknitelius/jsfwarn)


## Usage
Include JsfWarn dependency in your WebApp:
```xml
  <dependency>
    <groupId>com.knitelius</groupId>
    <artifactId>jsfwarn</artifactId>
    <version>0.0.11</version>
  </dependency>
```
Implement a WarningValidator:
```java
@Named
public class FooWarningValidator implements WarningValidator{
    @Inject
    private FooBean fooBean;

    @Override
    public void process(FacesContext context, UIInput component, ValidationResult validationResult) {
        if(!fooBean.isBarValid()) {
            validationResult.setFacesMessage(new FacesMessage(FacesMessage.SEVERITY_WARN, "FooBar", "This is a warning."));
        }
    }
}
```

Add the jw:warning tag to the component:
```xhtml
<h:inputText id="bar" value=...>
  <jw:warning validator="#{fooWarningValidator}" />
  <f:ajax event="change" render="@this" />
</h:inputText>
```


## Contributing

This project does not impose a specific development philosophy or
framework, so you're free to architect your code in the way that you want.

Everyone is welcome to contribute.

## License

This software is provided under the  Apache 2.0 license, read the `LICENSE.txt` file for details.
