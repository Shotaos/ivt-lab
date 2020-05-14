package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primaryTorpedoStore;
  private TorpedoStore secondaryTorpedoStore;

  @BeforeEach
  public void init(){
    this.primaryTorpedoStore = mock(TorpedoStore.class);
    this.secondaryTorpedoStore = mock(TorpedoStore.class);
    this.ship = new GT4500(this.primaryTorpedoStore, this.secondaryTorpedoStore);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(this.primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(this.primaryTorpedoStore.fire(1)).thenReturn(true);
    when(this.secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(this.secondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    // CHeck behaviour
    verify(primaryTorpedoStore, times(1)).isEmpty();
    verify(primaryTorpedoStore, times(1)).fire(1);
    verify(secondaryTorpedoStore, times(0)).isEmpty();
    verify(secondaryTorpedoStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(this.primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(this.primaryTorpedoStore.fire(1)).thenReturn(true);
    when(this.secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(this.secondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);

    // CHeck behaviour
    verify(primaryTorpedoStore, times(1)).fire(1);
    verify(secondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Signle_No_Torpedos(){
       // Arrange
       when(this.primaryTorpedoStore.isEmpty()).thenReturn(true);
       when(this.secondaryTorpedoStore.isEmpty()).thenReturn(true);
   
       // Act
       boolean result = ship.fireTorpedo(FiringMode.SINGLE);
   
       // Assert
       assertEquals(false, result);
   
       // CHeck behaviour
       verify(primaryTorpedoStore, times(1)).isEmpty();
       verify(primaryTorpedoStore, times(0)).fire(1);
       verify(secondaryTorpedoStore, times(1)).isEmpty();
       verify(secondaryTorpedoStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Signle_Primary_Empty(){
       // Arrange
       when(this.primaryTorpedoStore.isEmpty()).thenReturn(true);
       when(this.secondaryTorpedoStore.isEmpty()).thenReturn(false);
       when(this.secondaryTorpedoStore.fire(1)).thenReturn(true);
   
       // Act
       boolean result = ship.fireTorpedo(FiringMode.SINGLE);
   
       // Assert
       assertEquals(true, result);
   
       // CHeck behaviour
       verify(primaryTorpedoStore, times(1)).isEmpty();
       verify(primaryTorpedoStore, times(0)).fire(1);
       verify(secondaryTorpedoStore, times(1)).isEmpty();
       verify(secondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Signle_Test_Alternation(){
       // Arrange
       when(this.primaryTorpedoStore.isEmpty()).thenReturn(false);
       when(this.primaryTorpedoStore.fire(1)).thenReturn(true);
       when(this.secondaryTorpedoStore.isEmpty()).thenReturn(false);
       when(this.secondaryTorpedoStore.fire(1)).thenReturn(true);
   
       // Act
       boolean firstResult = ship.fireTorpedo(FiringMode.SINGLE);
       assertEquals(true, firstResult);
       verify(primaryTorpedoStore, times(1)).fire(1);
       verify(secondaryTorpedoStore, times(0)).fire(1);


       boolean secondResult = ship.fireTorpedo(FiringMode.SINGLE);
       assertEquals(true, secondResult);
       verify(primaryTorpedoStore, times(1)).fire(1);
       verify(secondaryTorpedoStore, times(1)).fire(1);

  }

  @Test
  public void fireTorpedo_Single_Report_Failure(){
    // Arrange
    when(this.primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(this.primaryTorpedoStore.fire(1)).thenReturn(false);
    when(this.secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(this.secondaryTorpedoStore.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);

    // CHeck behaviour
    verify(primaryTorpedoStore, times(1)).fire(1);
    verify(secondaryTorpedoStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_One_Failure_Returns_Success(){
    // Arrange
    when(this.primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(this.primaryTorpedoStore.fire(1)).thenReturn(true);
    when(this.secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(this.secondaryTorpedoStore.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);

    // CHeck behaviour
    verify(primaryTorpedoStore, times(1)).fire(1);
    verify(secondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Signle_Primary_Empty_Secondaty_Failure(){
    // Arrange
    when(this.primaryTorpedoStore.isEmpty()).thenReturn(true);
    when(this.secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(this.secondaryTorpedoStore.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);

    // CHeck behaviour
    verify(primaryTorpedoStore, times(0)).fire(1);
    verify(secondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Primary_Was_Fired_Last_Secondary_Empty(){
    // Arrange
    when(this.primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(this.primaryTorpedoStore.fire(1)).thenReturn(true);
    when(this.secondaryTorpedoStore.isEmpty()).thenReturn(true);
    when(this.secondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(true, result);

    // Assert
    result = ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(true, result);

    // CHeck behaviour
    verify(primaryTorpedoStore, times(2)).fire(1);
    verify(secondaryTorpedoStore, times(0)).fire(1);
  }

}
