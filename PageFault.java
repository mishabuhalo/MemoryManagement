/* It is in this file, specifically the replacePage function that will
   be called by MemoryManagement when there is a page fault.  The 
   users of this program should rewrite PageFault to implement the 
   page replacement algorithm.
*/

import java.util.*;

public class PageFault {

  /**
   * The page replacement algorithm for the memory management sumulator.
   * This method gets called whenever a page needs to be replaced.
   * <p>
   * <pre>
   *   Page page = ( Page ) mem.elementAt( oldestPage )
   * </pre>
   * This line brings the contents of the Page at oldestPage (a 
   * specified integer) from the mem vector into the page object.  
   * Next recall the contents of the target page, replacePageNum.  
   * Set the physical memory address of the page to be added equal 
   * to the page to be removed.
   * <pre>
   *   controlPanel.removePhysicalPage( oldestPage )
   * </pre>
   * Once a page is removed from memory it must also be reflected 
   * graphically.  This line does so by removing the physical page 
   * at the oldestPage value.  The page which will be added into 
   * memory must also be displayed through the addPhysicalPage 
   * function call.  One must also remember to reset the values of 
   * the page which has just been removed from memory.
   *
   * @param mem is the vector which contains the contents of the pages 
   *   in memory being simulated.  mem should be searched to find the 
   *   proper page to remove, and modified to reflect any changes.  
   * @param virtPageNum is the number of virtual pages in the 
   *   simulator (set in Kernel.java).  
   * @param replacePageNum is the requested page which caused the 
   *   page fault.  
   * @param controlPanel represents the graphical element of the 
   *   simulator, and allows one to modify the current display.
   */
  public static void replacePage ( Vector mem , int virtPageNum , int replacePageNum , ControlPanel controlPanel ) {

    int zeroClassPage = -1;
    int firsClassPage = -1;
    int secondClassPage = -1;
    int thirdClassPage = -1;
    int pageToReplace = -1;

    for (int i = 0; i < virtPageNum; i++)
    {
      Page page = (Page) mem.elementAt(i);
      if(page.physical!=-1) {
        if (page.R == 0 && page.M == 0) {
          if (zeroClassPage == -1) {
            zeroClassPage = i;
            break;
          }
        } else if (page.R == 0 && page.M == 1) {
          if (firsClassPage == -1)
            firsClassPage = i;
        } else if (page.R == 1 && page.M == 0) {
          if (secondClassPage == -1)
            secondClassPage = i;
        } else {
          if (thirdClassPage == -1)
            thirdClassPage = i;
        }
      }
    }

    if(zeroClassPage!=-1)
      pageToReplace = zeroClassPage;
    else if(firsClassPage!=-1)
      pageToReplace = firsClassPage;
    else if(secondClassPage!=-1)
      pageToReplace = secondClassPage;
    else
      pageToReplace = thirdClassPage;

    Page page = ( Page ) mem.elementAt( pageToReplace );
    Page nextpage = ( Page ) mem.elementAt( replacePageNum );
    controlPanel.removePhysicalPage( pageToReplace );
    nextpage.physical = page.physical;
    controlPanel.addPhysicalPage( nextpage.physical , replacePageNum );
    page.inMemTime = 0;
    page.lastTouchTime = 0;
    page.R = 0;
    page.M = 0;
    page.physical = -1;
  }
}
