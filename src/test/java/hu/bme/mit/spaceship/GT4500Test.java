package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primarytest;
  private TorpedoStore secondarytest;

  @BeforeEach
  public void init(){
    primarytest = mock(TorpedoStore.class);
    secondarytest = mock(TorpedoStore.class);
    
    this.ship = new GT4500(primarytest, secondarytest);
    //this.ship = new GT4500();
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primarytest.getTorpedoCount()).thenReturn(10);
    when(primarytest.fire(1)).thenReturn(true);
    when(secondarytest.getTorpedoCount()).thenReturn(10);
    when(secondarytest.fire(1)).thenReturn(true);
    
    // Act

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    // Assert
    assertEquals(true, result);
    verify(primarytest, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primarytest.getTorpedoCount()).thenReturn(10);
    when(primarytest.fire(1)).thenReturn(true);
    when(secondarytest.getTorpedoCount()).thenReturn(10);
    when(secondarytest.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);
    // Assert
    assertEquals(true, result);
    verify(primarytest, times(0)).fire(1);
    verify(secondarytest, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Failure() {
    // Arrange
    when(primarytest.getTorpedoCount()).thenReturn(0);
    when(primarytest.fire(1)).thenReturn(false);
    when(primarytest.isEmpty()).thenReturn(true);
    when(secondarytest.getTorpedoCount()).thenReturn(10);
    when(secondarytest.fire(1)).thenReturn(true);
    when(secondarytest.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primarytest, times(0)).fire(1);
    verify(secondarytest, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Failure() {
    // Arrange
    when(primarytest.getTorpedoCount()).thenReturn(0);
    when(primarytest.isEmpty()).thenReturn(true);
    when(secondarytest.getTorpedoCount()).thenReturn(0);
    when(secondarytest.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(primarytest, times(0)).fire(1);
    verify(secondarytest, times(0)).fire(1);
  }

}
